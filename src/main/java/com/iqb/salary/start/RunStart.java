package com.iqb.salary.start;

import com.iqb.salary.utils.FileGenerateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author chenghui
 */
@Component
public class RunStart implements ApplicationRunner {
    public static int allindex = 0;
    public static int successindex = 0;
    public static int failindex = 0;

    @Override
    public void run(ApplicationArguments var1) throws Exception {
        //parseExcel("X:\\日常工作\\标的信息修改-老数据无担保账户\\0518二次修改\\代偿历史数据.xlsx");
        //parseTXT("C:\\Users\\99369\\Desktop\\生产结果文件");
        //parseTXT2("C:\\Users\\99369\\Desktop\\全部标的.txt");
        //parseExcel2("C:\\Users\\99369\\Desktop\\0528担保账户添加.xlsx");
        //parseExcel3("D:\\6.4多还2000以内.xlsx");
        //parseExcel3("D:\\6.4多还2000以内.xlsx");
        readFile("D:\\3004-PAYTBN-612258-000001-20180612");
    }

    public void parseTXT2(String path) throws IOException {
        File file = new File(path);


        BufferedReader bf = new BufferedReader(new FileReader(file));
        // 读记录
        String line = bf.readLine();
        Set<String> allBorrowId = new HashSet<>();
        while (null != line) {
            if (StringUtils.isBlank(line.trim())) {
                line = bf.readLine();
                continue;
            }
            allBorrowId.add(line.trim());
            line = bf.readLine();
        }

        System.out.println("共有数据" + allBorrowId.size() + "条");
        Iterator<String> iterator = allBorrowId.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    public void parseTXT(String path) throws IOException {
        File dic = new File(path);
        if (dic.isDirectory()) {
            File[] files = dic.listFiles();
            for (File file : files) {
                BufferedReader bf = new BufferedReader(new FileReader(file));
                // 读记录
                String line = bf.readLine();
                while (null != line) {
                    // 处理文件
                    byte[] strLine = line.getBytes();
                    String retCode = new String(strLine, 10, 2).trim();
                    String borrowId = new String(strLine, 48, 40).trim();

                    if (retCode.equals("00")) {
                        successindex++;
                    } else {
                        failindex++;

                        System.out.println("返回结果：" + retCode + "  标ID：" + borrowId);
                    }

                    allindex++;
                    line = bf.readLine();
                }
            }

            System.out.println("共有数据" + allindex + "条，其中处理成功的条数：" + successindex + "；失败的条数：" + failindex);
        }
    }

    public void generateFile(List<List<String>> lists, int batchNo) throws IOException {
        String BATCH = "";
        List<String> sbrs = new ArrayList<>();
        for (List<String> list : lists) {
            String BANK = "3004";
            BATCH = String.valueOf(362598 + batchNo);
            String UNS_SERO = "000001";
            String BUS_TYPE = "01";
            String OLD_BIDNBR = list.get(0);
            String NEW_BIDNBR = "";
            String OLD_CARDNBRMY = "";
            String NEW_CARDNBRMY = "";
            String OLD_CARDNBRPE = "";
            String NEW_CARDNBRPE = "";
            String OLD_CARDNBRSU = "";
            String NEW_CARDNBRSU = list.get(1);
            String OLD_YIELD = "";
            String NEW_YIELD = "";
            String OLD_LOANTERM = "";
            String NEW_LOANTERM = "";
            String OLD_CARDNBR = "";
            String NEW_CARDNBR = "";
            String RESERVE = "";

            String sbr = "";
            //银行代号
            sbr += BANK;
            //批次号
            sbr += BATCH;
            //合作平台编号
            sbr += UNS_SERO;
            //业务类别
            sbr += BUS_TYPE;
            //原标的号
            sbr += StringUtils.rightPad(OLD_BIDNBR, 40, " ");
            //需修改成的标的号
            sbr += StringUtils.rightPad(NEW_BIDNBR, 40, " ");
            //原名义借款人电子账号
            sbr += StringUtils.rightPad(OLD_CARDNBRMY, 19, " ");
            //需修改成的名义借款人电子账号
            sbr += StringUtils.rightPad(NEW_CARDNBRMY, 19, " ");
            //原收款人电子账号
            sbr += StringUtils.rightPad(OLD_CARDNBRPE, 19, " ");
            //需修改成的收款人电子账号
            sbr += StringUtils.rightPad(NEW_CARDNBRPE, 19, " ");
            //原担保人电子账号
            sbr += StringUtils.rightPad(OLD_CARDNBRSU, 19, " ");
            //需修改成的担保人电子账号
            sbr += StringUtils.rightPad(NEW_CARDNBRSU, 19, " ");
            //原年化收益率
            sbr += StringUtils.leftPad(OLD_YIELD, 8, " ");
            //需修改成的年化收益率
            sbr += StringUtils.leftPad(NEW_YIELD, 8, " ");
            //原项目期限
            sbr += StringUtils.rightPad(OLD_LOANTERM, 4, " ");
            //需修改成的项目期限
            sbr += StringUtils.rightPad(NEW_LOANTERM, 4, " ");
            //原实际借款人电子账号
            sbr += StringUtils.rightPad(OLD_CARDNBR, 19, " ");
            //需修改成到的实际借款人电子账号
            sbr += StringUtils.rightPad(NEW_CARDNBR, 19, " ");
            //保留域
            sbr += StringUtils.rightPad(RESERVE, 362, " ");

            sbrs.add(sbr);

        }
        String fileName = "3004-BIDCG-000001-" + BATCH + "-20180523";

        System.out.println(fileName);

        FileGenerateUtil.writeup(sbrs, fileName, "X:\\日常工作\\标的信息修改-老数据无担保账户\\0518二次修改", true);

    }


    public void generateFile2(List<List<String>> lists) throws IOException {
        String BATCH = "528001";
        List<String> sbrs = new ArrayList<>();
        for (List<String> list : lists) {
            String BANK = "3004";
            String UNS_SERO = "000001";
            String BUS_TYPE = "01";
            String OLD_BIDNBR = list.get(0);
            String NEW_BIDNBR = "";
            String OLD_CARDNBRMY = "";
            String NEW_CARDNBRMY = "";
            String OLD_CARDNBRPE = "";
            String NEW_CARDNBRPE = "";
            String OLD_CARDNBRSU = "";
            String NEW_CARDNBRSU = list.get(1);
            String OLD_YIELD = "";
            String NEW_YIELD = "";
            String OLD_LOANTERM = "";
            String NEW_LOANTERM = "";
            String OLD_CARDNBR = "";
            String NEW_CARDNBR = "";
            String RESERVE = "";

            String sbr = "";
            //银行代号
            sbr += BANK;
            //批次号
            sbr += BATCH;
            //合作平台编号
            sbr += UNS_SERO;
            //业务类别
            sbr += BUS_TYPE;
            //原标的号
            sbr += StringUtils.rightPad(OLD_BIDNBR, 40, " ");
            //需修改成的标的号
            sbr += StringUtils.rightPad(NEW_BIDNBR, 40, " ");
            //原名义借款人电子账号
            sbr += StringUtils.rightPad(OLD_CARDNBRMY, 19, " ");
            //需修改成的名义借款人电子账号
            sbr += StringUtils.rightPad(NEW_CARDNBRMY, 19, " ");
            //原收款人电子账号
            sbr += StringUtils.rightPad(OLD_CARDNBRPE, 19, " ");
            //需修改成的收款人电子账号
            sbr += StringUtils.rightPad(NEW_CARDNBRPE, 19, " ");
            //原担保人电子账号
            sbr += StringUtils.rightPad(OLD_CARDNBRSU, 19, " ");
            //需修改成的担保人电子账号
            sbr += StringUtils.rightPad(NEW_CARDNBRSU, 19, " ");
            //原年化收益率
            sbr += StringUtils.leftPad(OLD_YIELD, 8, " ");
            //需修改成的年化收益率
            sbr += StringUtils.leftPad(NEW_YIELD, 8, " ");
            //原项目期限
            sbr += StringUtils.rightPad(OLD_LOANTERM, 4, " ");
            //需修改成的项目期限
            sbr += StringUtils.rightPad(NEW_LOANTERM, 4, " ");
            //原实际借款人电子账号
            sbr += StringUtils.rightPad(OLD_CARDNBR, 19, " ");
            //需修改成到的实际借款人电子账号
            sbr += StringUtils.rightPad(NEW_CARDNBR, 19, " ");
            //保留域
            sbr += StringUtils.rightPad(RESERVE, 362, " ");

            sbrs.add(sbr);

        }
        String fileName = "3004-BIDCG-000001-" + BATCH + "-20180529";

        System.out.println(fileName);

        FileGenerateUtil.writeup(sbrs, fileName, "C:\\Users\\99369\\Desktop\\", true);

    }

    public void parseExcel(String path) throws IOException {
        Workbook book = new XSSFWorkbook(new FileInputStream(ResourceUtils.getFile(path)));
        List<List<String>> resultList = new ArrayList<>();
        for (int s = 0; s < 4; s++) {
            Sheet sheet = book.getSheetAt(s);
            Row row;
            for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
                List<String> rowList = new ArrayList<>();
                row = sheet.getRow(i);
                if (row == null || row.getCell(0) == null) {
                    break;
                }

                String line1 = String.valueOf(Double.valueOf(row.getCell(0).getNumericCellValue()).intValue());
                rowList.add(line1);
                String line2 = row.getCell(2).getStringCellValue();
                rowList.add(line2);

                resultList.add(rowList);
            }

        }
        generateFile(resultList, 0);
    }

    public void parseExcel2(String path) throws IOException {
        Workbook book = new XSSFWorkbook(new FileInputStream(ResourceUtils.getFile(path)));
        List<List<String>> resultList = new ArrayList<>();

        Sheet sheet = book.getSheetAt(0);
        Row row;
        for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
            List<String> rowList = new ArrayList<>();
            row = sheet.getRow(i);
            if (row == null || row.getCell(0) == null) {
                break;
            }

            String line1 = String.valueOf(Double.valueOf(row.getCell(0).getNumericCellValue()).intValue());
            rowList.add(line1);
            String line2 = row.getCell(2).getStringCellValue();
            rowList.add(line2);

            resultList.add(rowList);
        }
        System.out.println(resultList.size());
        generateFile2(resultList);
    }

    /**
     * 重复还款调账
     *
     * @param path
     * @throws IOException
     */
    public void parseExcel3(String path) throws IOException {
        Workbook book = new XSSFWorkbook(new FileInputStream(ResourceUtils.getFile(path)));
        List<List<String>> resultList = new ArrayList<>();
        Sheet sheet = book.getSheetAt(0);
        Row row;
        for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
            List<String> rowList = new ArrayList<>();
            row = sheet.getRow(i);
            if (row == null || row.getCell(0) == null) {
                break;
            }
            //电子账号
            String line1 = row.getCell(0).getStringCellValue();
            rowList.add(line1);
            //客户姓名
            String line2 = row.getCell(1).getStringCellValue();
            rowList.add(line2);
            //应扣金额
            String line3 = (new BigDecimal(String.valueOf(row.getCell(2).getNumericCellValue())).multiply(new BigDecimal("100"))).stripTrailingZeros().toPlainString();
            rowList.add(line3);

            resultList.add(rowList);
        }
        System.out.println(resultList.size());
        generateFile3(resultList);
    }

    /**
     * 重复还款调账生成文件
     *
     * @param lists
     * @throws IOException
     */
    public void generateFile3(List<List<String>> lists) throws IOException {
        String BATCH = "612258";
        String TIME = "20180612";
        List<String> sbrs = new ArrayList<>();
        for (List<String> list : lists) {
            String BANK = "3004";
            String BRANCH = "9999";
            String BRNO = "000001";
            String TELLER = "999999";
            //生产环境使用
            String TYPE = "101";
            //uat环境使用
            //String TYPE = "100";
            String DATE = TIME;
            String REF = "";
            String AMOUNT = list.get(2);
            String CURR = "156";
            String DEALDATE = TIME;
            String CARDNBR = list.get(0);
            String NAME = list.get(1);
            String RESE = "";

            String sbr = "";
            //银行代号
            sbr += BANK;
            //分行代号
            sbr += BRANCH;
            //网点代号
            sbr += BRNO;
            //柜员代码
            sbr += TELLER;
            //批号
            sbr += BATCH;
            //业务类别
            sbr += TYPE;
            //文件传送日期
            sbr += DATE;
            //参考信息
            sbr += StringUtils.rightPad(REF, 19, "");
            //应扣金额
            sbr += StringUtils.leftPad(AMOUNT, 12, "0");
            //币种
            sbr += CURR;
            //处理日期
            sbr += DEALDATE;
            //电子账号
            sbr += StringUtils.rightPad(CARDNBR, 19, "");
            //客户姓名
            sbr += StringUtils.rightPad(NAME, 60 - (NAME.length()), "");
            //保留域
            sbr += StringUtils.rightPad(RESE, 100, "");
            sbrs.add(sbr);
        }
        String fileName = "3004-PAYREQN-" + BATCH + "-000001-" + TIME;
        System.out.println(fileName);
        //FileGenerateUtil.writeup(sbrs, fileName, "D:\\", true);
        FileGenerateUtil.writeup2(sbrs, fileName, "D:\\", true);
    }

    public void readFile(String filePath) {
        InputStream inputStream = null;
        InputStreamReader inputReader = null;
        BufferedReader bufferReader = null;
        try {
            inputStream = new FileInputStream(filePath);
            inputReader = new InputStreamReader(inputStream);
            bufferReader = new BufferedReader(inputReader);
            // 读取一行
            String line = "";
            BigDecimal ss = BigDecimal.ZERO;
            BigDecimal aa = BigDecimal.ZERO;
            while ((line = bufferReader.readLine()) != null) {
                if (StringUtils.isEmpty(line)) {
                    continue;
                }
                String result = line.substring(111 - 1, 112).trim();
                if ("00".equals(result)) {
                    BigDecimal amount = BigDecimal.valueOf(Double.valueOf(line.substring(57 - 1, 68).trim()) / 100);
                    String cardnbr = line.substring(92 - 1, 110).trim();
                    //System.out.println(result+"-"+cardnbr+"-"+amount);
                    ss = ss.add(amount);
                } else {
                    System.out.println(result+"-"+line.substring(92 - 1, 110).trim()+"-"+BigDecimal.valueOf(Double.valueOf(line.substring(57 - 1, 68).trim()) / 100));
                    //aa = aa.add(BigDecimal.valueOf(Double.valueOf(line.substring(57 - 1, 68).trim()) / 100));
                }
            }
            //System.out.println(ss);
            //System.out.println(aa);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
