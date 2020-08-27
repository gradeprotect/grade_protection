package com.das.utils;

import com.das.entity.SysInfo;
import com.das.service.DepartmentService;
import com.das.service.SysInfoService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 许文滨
 * @date 2020-8-20
 * @descrioption 读取前端传来的 excel 文件信息
 */
@Service
public class ReadExcelContents {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private SysInfoService sysInfoService;

    public static List<Integer> fail;

    /**
     * 读取excel文件 如果文件格式不合法则返回null
     * @param multipartFile 文件流
     * @param importer_id 导入员id
     * @return 所有的合法数据信息
     * @throws Exception
     */
    public List<SysInfo> readExcel(MultipartFile multipartFile, Integer importer_id) throws Exception{
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
                fail = new ArrayList<>();
                //根据部门名和系统名判断该系统是否在数据库中
                List<SysInfo> sysInfos = sysInfoService.getAll();
                Map<Integer, Set<String>> judgment = new HashMap<>(sysInfos.size()/4);
                for (SysInfo sysInfo:sysInfos){
                    Set<String> tmp = judgment.getOrDefault(sysInfo.getDepartment_id(),new HashSet<>());
                    tmp.add(sysInfo.getName());
                    judgment.put(sysInfo.getDepartment_id(),tmp);
                }
                //读取数据
                for (int i=1;i<=rowNum;i++){
                    row = sheet.getRow(i);
                    //如果输入的信息系统名或部门名为空,或系统名不存在，则添加失败
                    if (getCellFormatValue(row.getCell(2)) == null ||
                            departmentService.getIdByName(String.valueOf(getCellFormatValue(row.getCell(2)))) == null ||
                            getCellFormatValue(row.getCell(0)) == null){
                        fail.add(i+1);
                        continue;
                    }else {
                        Set<String> tmp = judgment.getOrDefault(departmentService.getIdByName(String.valueOf(getCellFormatValue(row.getCell(2)))),new HashSet<>());
                        if (!tmp.add(String.valueOf(getCellFormatValue(row.getCell(0))))){
                            fail.add(i+1);
                            continue;
                        }
                    }
                    //根据excel中的信息构建SysInfo对象
                    SysInfo sysInfo = new SysInfo(null,String.valueOf(getCellFormatValue(row.getCell(0))),
                            new Date(), importer_id,null,1,null,null,
                            getCellFormatValue(row.getCell(1))==null?null:Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(1)))),
                            departmentService.getIdByName(String.valueOf(getCellFormatValue(row.getCell(2)))),null,
                            getCellFormatValue(row.getCell(3))==null?null:String.valueOf(getCellFormatValue(row.getCell(3))),
                            getCellFormatValue(row.getCell(4))==null?null:String.valueOf(getCellFormatValue(row.getCell(4))),
                            getCellFormatValue(row.getCell(5))==null?null:String.valueOf(getCellFormatValue(row.getCell(5))),
                            getCellFormatValue(row.getCell(6))==null?null:String.valueOf(getCellFormatValue(row.getCell(6))),
                            getCellFormatValue(row.getCell(7))==null?null:String.valueOf(getCellFormatValue(row.getCell(7))),
                            getCellFormatValue(row.getCell(8))==null?null:String.valueOf(getCellFormatValue(row.getCell(8))),
                            getCellFormatValue(row.getCell(9))==null?null:Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(9)))),
                            getCellFormatValue(row.getCell(10))==null?null:Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(10)))),
                            getCellFormatValue(row.getCell(11))==null?null:Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(11)))),
                            getCellFormatValue(row.getCell(12))==null?null:Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(12)))),
                            getCellFormatValue(row.getCell(13))==null?null:Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(13)))),
                            getCellFormatValue(row.getCell(14))==null?null:Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(14)))),
                            getCellFormatValue(row.getCell(15))==null?null:Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(15)))),
                            getCellFormatValue(row.getCell(16))==null?null:Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(16)))),
                            getCellFormatValue(row.getCell(17))==null?null:Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(17)))),
                            getCellFormatValue(row.getCell(18))==null?null:Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(18)))),
                            getCellFormatValue(row.getCell(19)) != null && Boolean.parseBoolean(String.valueOf(getCellFormatValue(row.getCell(19)))),
                            getCellFormatValue(row.getCell(20)) != null && Boolean.parseBoolean(String.valueOf(getCellFormatValue(row.getCell(20)))),
                            getCellFormatValue(row.getCell(21)) != null && Boolean.parseBoolean(String.valueOf(getCellFormatValue(row.getCell(21)))),
                            getCellFormatValue(row.getCell(22)) != null && Boolean.parseBoolean(String.valueOf(getCellFormatValue(row.getCell(22)))),
                            getCellFormatValue(row.getCell(23)) != null && Boolean.parseBoolean(String.valueOf(getCellFormatValue(row.getCell(23)))),
                            getCellFormatValue(row.getCell(24)) != null && Boolean.parseBoolean(String.valueOf(getCellFormatValue(row.getCell(24)))),
                            getCellFormatValue(row.getCell(25)) != null && Boolean.parseBoolean(String.valueOf(getCellFormatValue(row.getCell(25)))),
                            getCellFormatValue(row.getCell(26))==null?null:String.valueOf(getCellFormatValue(row.getCell(26))),
                            getCellFormatValue(row.getCell(27))==null||getCellFormatValue(row.getCell(31)).equals("")?null:new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(getCellFormatValue(row.getCell(27)))),
                            getCellFormatValue(row.getCell(28))==null?null:Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(28)))),
                            getCellFormatValue(row.getCell(29))==null?null:Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(29)))),
                            getCellFormatValue(row.getCell(30))==null?null:Integer.valueOf(String.valueOf(getCellFormatValue(row.getCell(30)))),
                            getCellFormatValue(row.getCell(31))==null||getCellFormatValue(row.getCell(31)).equals("")?null:new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(getCellFormatValue(row.getCell(31)))),
                            getCellFormatValue(row.getCell(32)) != null && Boolean.parseBoolean(String.valueOf(getCellFormatValue(row.getCell(32)))),
                            getCellFormatValue(row.getCell(33))==null?null:String.valueOf(getCellFormatValue(row.getCell(33))),
                            getCellFormatValue(row.getCell(34)) != null && Boolean.parseBoolean(String.valueOf(getCellFormatValue(row.getCell(34)))));
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
        if (cell != null && !"".equals(cell)) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                case Cell.CELL_TYPE_FORMULA: {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                        cellValue = formater.format(date);
                    } else {
                        cellValue = (int)cell.getNumericCellValue();
                    }
                    break;
                }
                case Cell.CELL_TYPE_BOOLEAN:
                    cellValue = cell.getBooleanCellValue();
                    break;
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
        if (colNum != 36) {
            return false;
        }
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
