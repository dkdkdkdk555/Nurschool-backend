package com.nurse.school.service;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExcelUtil {

    // 각 셀의 데이터타입에 맞게 값 가져오기
    public String getCellValue(Cell cell) {

        String value = "";

        if(cell == null){
            return value;
        }

        switch (cell.getCellType()) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            case NUMERIC:
                value = (int) cell.getNumericCellValue() + "";
                break;
            default:
                break;
        }
        return value;
    }

    // 엑셀파일의 데이터 목록 가져오기 (파라미터들은 위에서 설명함)
    public List<Map<String, Object>> getListData(MultipartFile file, int startRowNum, int columnLength, String type, String extension) {

        List<Map<String, Object>> excelList = new ArrayList<Map<String,Object>>();

        try {

            int rowIndex = 0;
            int columnIndex = 0;

            if(extension.equals("XSSF")) {
                OPCPackage opcPackage = OPCPackage.open(file.getInputStream());
                // 리소스
                XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
                // 첫번째 시트
                XSSFSheet sheet = workbook.getSheetAt(0);

                // 업로드 엑셀 양식이 아니면 null을 반환
                if (!isMatchExcelForm(type, sheet)) return null;

                // 첫번째 행(0)은 컬럼 명이기 때문에 두번째 행(1) 부터 검색
                for (rowIndex = startRowNum; rowIndex < sheet.getLastRowNum() + 1; rowIndex++) {
                    XSSFRow row = sheet.getRow(rowIndex);

                    // 빈 행은 Skip
                    if (row.getCell(0) != null && !row.getCell(0).toString().isBlank()) {

                        Map<String, Object> map = new HashMap<>();

                        int cells = columnLength;

                        for (columnIndex = 0; columnIndex <= cells; columnIndex++) {
                            XSSFCell cell = row.getCell(columnIndex);
                            map.put(String.valueOf(columnIndex), getCellValue(cell));
//                        logger.info(rowIndex + " 행 : " + columnIndex+ " 열 = " + getCellValue(cell));
                        }

                        excelList.add(map);
                    }
                }
            } else if(extension.equals("HSSF")){
                HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
                HSSFSheet sheet = workbook.getSheetAt(0);

                if (!isMatchExcelForm(type, sheet)) return null;

                for (rowIndex = startRowNum; rowIndex < sheet.getLastRowNum() + 1; rowIndex++) {
                    HSSFRow row = sheet.getRow(rowIndex);

                    // 빈 행은 Skip
                    if (row.getCell(0) != null && !row.getCell(0).toString().isBlank()) {

                        Map<String, Object> map = new HashMap<>();

                        int cells = columnLength;

                        for (columnIndex = 0; columnIndex <= cells; columnIndex++) {
                            HSSFCell cell = row.getCell(columnIndex);
                            map.put(String.valueOf(columnIndex), getCellValue(cell));
//                        logger.info(rowIndex + " 행 : " + columnIndex+ " 열 = " + getCellValue(cell));

                        }

                        excelList.add(map);
                    }
                }


            }
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return excelList;
    }

    boolean isMatchExcelForm(String formType, Sheet sheet){
        if(formType.equals("person")){
            if("학년".equals(sheet.getRow(0).getCell(0)) &&
                    "반".equals(sheet.getRow(0).getCell(1)) &&
                    "번호".equals(sheet.getRow(0).getCell(2)) &&
                    "성명".equals(sheet.getRow(0).getCell(3)) &&
                    "학생개인번호".equals(sheet.getRow(0).getCell(4)) &&
                    "성별".equals(sheet.getRow(0).getCell(5))){
                return false;
            }
        } else if(formType.equals("medicine")){
            if("순번".equals(sheet.getRow(2).getCell(0)) &&
                    "내용".equals(sheet.getRow(2).getCell(1)) &&
                    "규격".equals(sheet.getRow(2).getCell(2)) &&
                    "수량".equals(sheet.getRow(2).getCell(3))){
                return false;
            }
        }

        return true;
    }

}
