package com.thd.store.service.impl;

import com.thd.store.dto.administrativeUnit.AdministrativeUnitImport;
import com.thd.store.exception.StoreException;
import com.thd.store.service.ExcelService;
import com.thd.store.type.AdministrativeUnitLevel;
import com.thd.store.util.SystemMessage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.thd.store.util.ConstUtil.DOT_XLS;
import static com.thd.store.util.ConstUtil.DOT_XLSX;

/**
 * @author DatNuclear 17/04/2024
 * @project store
 */
@Service
public class ExcelServiceImpl extends BaseService implements ExcelService {
    private static final Map<String, Integer> administrativeUnitColumn = new HashMap<>();

    static {
        administrativeUnitColumn.put("provinceName", 0);
        administrativeUnitColumn.put("provinceCode", 1);
        administrativeUnitColumn.put("districtName", 2);
        administrativeUnitColumn.put("districtCode", 3);
        administrativeUnitColumn.put("communeName", 4);
        administrativeUnitColumn.put("communeCode", 5);
    }

    @Override
    public List<AdministrativeUnitImport> readAdministrativeUnit(MultipartFile file) {
        if (file == null || !StringUtils.hasText(file.getOriginalFilename())) {
            throw new IllegalArgumentException(getMessage(SystemMessage.FILE_NAME_INVALID));
        }
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook;
            Map<String, AdministrativeUnitImport> administrativeUnitImportMap = new LinkedHashMap<>();
            if (file.getOriginalFilename().endsWith(DOT_XLSX)) {
                workbook = new XSSFWorkbook(inputStream);
            } else if (file.getOriginalFilename().endsWith(DOT_XLS)) {
                workbook = new HSSFWorkbook(inputStream);
            } else {
                throw new IllegalArgumentException(getMessage(SystemMessage.FILE_NAME_INVALID));
            }
            Sheet sheet = workbook.getSheetAt(0);
            Row row;
            int rowIndex = 1;
            String provinceCode;
            String provinceName;
            String districtCode;
            String districtName;
            String communeCode;
            String communeName;
            while (rowIndex <= sheet.getLastRowNum()) {
                row = sheet.getRow(rowIndex++);
                if (row != null) {
                    provinceName = getStringValueCell(row.getCell(administrativeUnitColumn.get("provinceName")));
                    provinceCode = getStringValueCell(row.getCell(administrativeUnitColumn.get("provinceCode")));
                    districtName = getStringValueCell(row.getCell(administrativeUnitColumn.get("districtName")));
                    districtCode = getStringValueCell(row.getCell(administrativeUnitColumn.get("districtCode")));
                    communeName = getStringValueCell(row.getCell(administrativeUnitColumn.get("communeName")));
                    communeCode = getStringValueCell(row.getCell(administrativeUnitColumn.get("communeCode")));
                    if (!administrativeUnitImportMap.containsKey(provinceCode)) {
                        administrativeUnitImportMap.put(provinceCode,
                                new AdministrativeUnitImport(provinceName, provinceCode, null, AdministrativeUnitLevel.PROVINCE.getLevel()));
                    }
                    if (!administrativeUnitImportMap.containsKey(districtCode)) {
                        administrativeUnitImportMap.put(districtCode,
                                new AdministrativeUnitImport(districtName, districtCode, provinceCode,AdministrativeUnitLevel.DISTRICT.getLevel()));
                    }
                    if (!administrativeUnitImportMap.containsKey(communeCode)) {
                        administrativeUnitImportMap.put(communeCode,
                                new AdministrativeUnitImport(communeName, communeCode, districtCode,AdministrativeUnitLevel.COMMUNE.getLevel()));
                    }
                }
            }
            return new ArrayList<>(administrativeUnitImportMap.values());
        } catch (IOException e) {
            throw new StoreException(e.getMessage());
        }
    }

    private static String getStringValueCell(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> null;
        };
    }
}
