package com.das.utils;

import com.das.entity.SysInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 许文滨
 * @date 2020-8-20
 * @descrioption 读取前端传来的 excel 文件信息
 */
public class ReadExcelContents {
    public static List<SysInfo> readExcel(MultipartFile multipartFile, Integer importer_id) throws Exception{
        List<SysInfo> content = new ArrayList<>();
        Workbook workbook = getWb(multipartFile);
        if (workbook == null){
            throw new Exception("WorkBook 对象为空");
        }else {
            Sheet sheet = workbook.getSheetAt(0);
            //获取总行数
            int rowNum = sheet.getLastRowNum();
            Row row = sheet.getRow(0);
            //获取总列数
            int colNum = row.getPhysicalNumberOfCells();
            if (!isCompliance(row,colNum)){
                return null;
            }else {
                //读取数据
                for (int i=1;i<rowNum;i++){
                    row = sheet.getRow(i);
                    SysInfo sysInfo = new SysInfo(null,String.valueOf(getCellFormatValue(row.getCell(0))),
                            new Date(), Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(1)))),
                            importer_id,1,null,null,null,
                            Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(2)))),null,
                            String.valueOf(getCellFormatValue(row.getCell(3))),
                            String.valueOf(getCellFormatValue(row.getCell(4))),
                            String.valueOf(getCellFormatValue(row.getCell(5))),
                            String.valueOf(getCellFormatValue(row.getCell(6))),
                            String.valueOf(getCellFormatValue(row.getCell(7))),
                            String.valueOf(getCellFormatValue(row.getCell(8))),
                            Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(9)))),
                            Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(10)))),
                            Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(11)))),
                            Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(12)))),
                            Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(13)))),
                            Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(14)))),
                            Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(15)))),
                            Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(16)))),
                            Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(17)))),
                            Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(18)))),
                            Boolean.valueOf(String.valueOf(getCellFormatValue(row.getCell(19)))),
                            Boolean.valueOf(String.valueOf(getCellFormatValue(row.getCell(20)))),
                            Boolean.valueOf(String.valueOf(getCellFormatValue(row.getCell(21)))),
                            Boolean.valueOf(String.valueOf(getCellFormatValue(row.getCell(22)))),
                            Boolean.valueOf(String.valueOf(getCellFormatValue(row.getCell(23)))),
                            Boolean.valueOf(String.valueOf(getCellFormatValue(row.getCell(24)))),
                            Boolean.valueOf(String.valueOf(getCellFormatValue(row.getCell(25)))),
                            String.valueOf(getCellFormatValue(row.getCell(26))),
                            new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(getCellFormatValue(row.getCell(27)))),
                            Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(28)))),
                            Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(29)))),
                            Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(30)))),
                            new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(getCellFormatValue(row.getCell(31)))),
                            Boolean.valueOf(String.valueOf(getCellFormatValue(row.getCell(32)))),
                            String.valueOf(getCellFormatValue(row.getCell(33))),
                            Boolean.valueOf(String.valueOf(getCellFormatValue(row.getCell(34)))));
                    content.add(sysInfo);
                }
            }
        }
        return content;
    }

    /**
     * 根据 cell 类型设置数据
     * @param cell Cell
     * @return Object
     */
    private static Object getCellFormatValue(Cell cell) {
        Object cellValue = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                case Cell.CELL_TYPE_FORMULA: {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        cellValue = date;
                    } else {
                        cellValue = (int)cell.getNumericCellValue();
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING:
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                default:
            }
        } else {
            cellValue = null;
        }
        return cellValue;
    }

    /**
     * 判断 excel 表格的格式是否合规
     * @param row Row
     * @return boolean
     */
    private static boolean isCompliance(Row row,int colNum){
        if (colNum != 35) {
            return false;
        }
        return "name".equals(getCellFormatValue(row.getCell(0))) &&
                "grade_protect_level".equals(getCellFormatValue(row.getCell(1))) &&
                "department".equals(getCellFormatValue(row.getCell(2))) &&
                "business_type".equals(getCellFormatValue(row.getCell(3))) &&
                "business_desc".equals(getCellFormatValue(row.getCell(4))) &&
                "service_area".equals(getCellFormatValue(row.getCell(5))) &&
                "service_obj".equals(getCellFormatValue(row.getCell(6))) &&
                "network".equals(getCellFormatValue(row.getCell(7))) &&
                "sys_interconnect".equals(getCellFormatValue(row.getCell(8))) &&
                "safety_num".equals(getCellFormatValue(row.getCell(9))) &&
                "domestic_safety_num".equals(getCellFormatValue(row.getCell(10))) &&
                "network_num".equals(getCellFormatValue(row.getCell(11))) &&
                "domestic_network_num".equals(getCellFormatValue(row.getCell(12))) &&
                "operate_sys_num".equals(getCellFormatValue(row.getCell(13))) &&
                "domestic_operate_sys_num".equals(getCellFormatValue(row.getCell(14))) &&
                "database_num".equals(getCellFormatValue(row.getCell(15))) &&
                "domestic_database_num".equals(getCellFormatValue(row.getCell(16))) &&
                "server_num".equals(getCellFormatValue(row.getCell(17))) &&
                "domestic_server_num".equals(getCellFormatValue(row.getCell(18))) &&
                "rank_evaluation".equals(getCellFormatValue(row.getCell(19))) &&
                "risk_assessment".equals(getCellFormatValue(row.getCell(20))) &&
                "disaster_recover".equals(getCellFormatValue(row.getCell(21))) &&
                "emergency_response".equals(getCellFormatValue(row.getCell(22))) &&
                "sys_integration".equals(getCellFormatValue(row.getCell(23))) &&
                "security_advisory".equals(getCellFormatValue(row.getCell(24))) &&
                "security_train".equals(getCellFormatValue(row.getCell(25))) &&
                "evaluate_firm_name".equals(getCellFormatValue(row.getCell(26))) &&
                "put_into_time".equals(getCellFormatValue(row.getCell(27))) &&
                "business_info_level".equals(getCellFormatValue(row.getCell(28))) &&
                "sys_service_level".equals(getCellFormatValue(row.getCell(29))) &&
                "info_sys_level".equals(getCellFormatValue(row.getCell(30))) &&
                "rank_time".equals(getCellFormatValue(row.getCell(31))) &&
                "expert_review".equals(getCellFormatValue(row.getCell(32))) &&
                "competent_authority_name".equals(getCellFormatValue(row.getCell(33))) &&
                "sys_rate_report".equals(getCellFormatValue(row.getCell(34)));
    }

    /**
     * 将 MultipartFile 文件转换为 Workbook 文件
     * @param multipartFile MultipartFile
     * @return Workbook
     */
    private static Workbook getWb(MultipartFile multipartFile){
        String filepath = multipartFile.getOriginalFilename();
        String ext = filepath.substring(filepath.lastIndexOf("."));
        Workbook workbook = null;
        try {
            InputStream is = multipartFile.getInputStream();
            if(".xls".equals(ext)){
                workbook = new HSSFWorkbook(is);
            }else if(".xlsx".equals(ext)){
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }
}
