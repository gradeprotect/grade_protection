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
                System.out.println("++++++++++++++++++++++++++++");
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
                case
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
        for (int i=0;i<35;i++){
            System.out.println("----------------------"+getCellFormatValue(row.getCell(i))+"-----------------");
        }
        System.out.println("-------------------------------------------");
        return "系统名称".equals(getCellFormatValue(row.getCell(0))) &&
                "等保级别".equals(getCellFormatValue(row.getCell(1))) &&
                "部门名称".equals(getCellFormatValue(row.getCell(2))) &&
                "业务类型".equals(getCellFormatValue(row.getCell(3))) &&
                "业务描述".equals(getCellFormatValue(row.getCell(4))) &&
                "服务范围".equals(getCellFormatValue(row.getCell(5))) &&
                "服务对象".equals(getCellFormatValue(row.getCell(6))) &&
                "网络性质".equals(getCellFormatValue(row.getCell(7))) &&
                "系统互联情况".equals(getCellFormatValue(row.getCell(8))) &&
                "安全产品数".equals(getCellFormatValue(row.getCell(9))) &&
                "国产安全产品数".equals(getCellFormatValue(row.getCell(10))) &&
                "网络产品数".equals(getCellFormatValue(row.getCell(11))) &&
                "国产网络产品数".equals(getCellFormatValue(row.getCell(12))) &&
                "操作系统数".equals(getCellFormatValue(row.getCell(13))) &&
                "国产操作系统数".equals(getCellFormatValue(row.getCell(14))) &&
                "数据库数".equals(getCellFormatValue(row.getCell(15))) &&
                "国产数据库数".equals(getCellFormatValue(row.getCell(16))) &&
                "服务器数".equals(getCellFormatValue(row.getCell(17))) &&
                "国产服务器数".equals(getCellFormatValue(row.getCell(18))) &&
                "等级评测".equals(getCellFormatValue(row.getCell(19))) &&
                "风险评估".equals(getCellFormatValue(row.getCell(20))) &&
                "灾难恢复".equals(getCellFormatValue(row.getCell(21))) &&
                "紧急响应".equals(getCellFormatValue(row.getCell(22))) &&
                "系统集成".equals(getCellFormatValue(row.getCell(23))) &&
                "安全咨询".equals(getCellFormatValue(row.getCell(24))) &&
                "安全培训".equals(getCellFormatValue(row.getCell(25))) &&
                "等级评测单位名称".equals(getCellFormatValue(row.getCell(26))) &&
                "投入使用时间".equals(getCellFormatValue(row.getCell(27))) &&
                "业务信息安全保护等级".equals(getCellFormatValue(row.getCell(28))) &&
                "系统服务安全保护等级".equals(getCellFormatValue(row.getCell(29))) &&
                "信息系统安全保护等级".equals(getCellFormatValue(row.getCell(30))) &&
                "定级时间".equals(getCellFormatValue(row.getCell(31))) &&
                "专家评审情况".equals(getCellFormatValue(row.getCell(32))) &&
                "主管部门名称".equals(getCellFormatValue(row.getCell(33))) &&
                "系统定级报告".equals(getCellFormatValue(row.getCell(34)));
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
