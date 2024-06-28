package romanow.abc.desktop;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;
import romanow.abc.core.ErrorList;
import romanow.abc.core.entity.metadata.StreamDataList;
import romanow.abc.core.entity.metadata.StreamDataValue;
import romanow.abc.core.utils.OwnDateTime;
import romanow.abc.core.utils.OwnTime;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ESSStreamDataExport {
    public static void exportToXLS(String fName, ErrorList errors, ArrayList<StreamDataList> data, boolean oneList){
        int listCount=0;
        int maxSize=0;
        for(StreamDataList dd : data){
            if (dd.isSuccess())
                listCount++;
            int size = dd.getData().size();
            if (size > maxSize)
                maxSize = size;
            }
        if (listCount==0){
            errors.addInfo("Список потоковых данных пуст");
            return;
            }
        //String fName = fnameloggerFilePath +"/"+ getClass().getSimpleName()+"_"+new OwnDateTime().toString()+".xlsx";
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Данные");
        CellStyle style = workbook.createCellStyle();
        DataFormat df = workbook.createDataFormat();
        style.setDataFormat(df.getFormat("dd.mm.yy hh:mm:ss;@"));
        //------------------------------------------------------------------------------------------
        Row row;
        Cell cell;
        ArrayList<StreamDataValue> colData;
        int ncol=0;
        row = sheet.createRow(0);
        for(StreamDataList dd : data){
            row.createCell(ncol*2).setCellValue(dd.title);
            ncol++;
            }
        for(int nrow=0; nrow<maxSize;nrow++){
            row = sheet.createRow(nrow+1);
            ncol=0;
            for(StreamDataList dd : data){
                colData = dd.getData();
                if (nrow>=colData.size()){
                    ncol++;
                    continue;
                    }
                cell = row.createCell(ncol*2);
                StreamDataValue vv = colData.get(nrow);
                OwnDateTime dzz = new OwnDateTime(vv.timeStamp);
                cell.setCellValue(OwnTime.toXLSFormat(vv.timeStamp));
                cell.setCellStyle(style);
                cell = row.createCell(ncol*2+1);
                cell.setCellValue(vv.value);
                ncol++;
                }
            }
        //------------------------------------------------------------------------------------------
        // Создаем холст
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        // Первые четыре значения по умолчанию: 0, [0,5]: начало с 0 столбца и 5 строк; [7,26]: ширина 7 ячеек, 26 расширяется до 26 строк
        // Ширина по умолчанию (14-8) * 12
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, listCount*2+1, 2, listCount*2+31, 62);
        // Создаем объект диаграммы
        XSSFChart chart = drawing.createChart(anchor);
        //Заголовок
        chart.setTitleText ("Потоковые данные");
        // перезапись заголовка
        chart.setTitleOverlay(false);
        // Положение легенды
        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.TOP);
        // Метка оси классификации (ось X), позиция заголовка
        XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        bottomAxis.setTitle ("Время");
        // Ось значений (ось Y), позиция заголовка
        XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
        leftAxis.setTitle ("Cобытия");
        // CellRangeAddress (номер начальной строки, номер конечной строки, номер начального столбца, номер конечного столбца)
        // Данные оси классификации (ось X), положение диапазона ячеек от [0, 0] до [0, 6]
        //Double dd2[] = new Double[dd.length];
        //Double dd3[] = new Double[dd.length];
        //for(int i=0;i<dd.length;i++){
        //    dd2[i]=new Double(dd[i]);
        //    dd3[i]=new Double(ff0+i*step);
        //    }
        //XDDFNumericalDataSource<Double> ff2 = XDDFDataSourcesFactory.fromArray(dd2);
        //XDDFNumericalDataSource<Double> ff3 = XDDFDataSourcesFactory.fromArray(dd3);
        ncol=0;
        for(StreamDataList dd : data){
            colData = dd.getData();
            int vv1 =  colData.size();
            int vv2 = ncol*2;
            XDDFNumericalDataSource<Double> ff2 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, vv1+1, vv2+1, vv2+1));
            boolean stringType = false; // colData.get(0).type==Bundle.typeString;
            XDDFNumericalDataSource<Double> ff3= XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, vv1+1, vv2, vv2));
            // LINE: линейный график,
            XDDFScatterChartData xData = (XDDFScatterChartData) chart.createData(ChartTypes.SCATTER, bottomAxis, leftAxis);
            // Данные загрузки графика, пунктирная линия 1
            XDDFScatterChartData.Series series1 = (XDDFScatterChartData.Series) xData.addSeries(ff3, ff2);
            series1.setTitle (dd.title, null);
            //Прямой
            series1.setSmooth(false);
            // Устанавливаем размер метки
            //series1.setMarkerSize((short) 6);
            // Устанавливаем стиль метки, звездочки
            series1.setMarkerStyle(MarkerStyle.CIRCLE);
            //Рисовать
            chart.plot(xData);
            ncol++;
            }
        errors.addInfo("Экспорт ПД ("+listCount+") в "+fName);
        //------------------------------------------------------------------------------------------
        try (FileOutputStream out = new FileOutputStream(fName)) {
            workbook.write(out);
            out.close();
            } catch (IOException e)
                { errors.addError(fName+" - ошибка экспорта: "+e.toString());  }
    }


}
