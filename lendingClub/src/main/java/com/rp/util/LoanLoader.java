package com.rp.util;

import com.rp.p2p.loan.BrowseLoansResultDao;
import com.rp.p2p.model.*;
import org.apache.log4j.Logger;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LoanLoader {
    private static final Logger logger_ = Logger.getLogger(LoanLoader.class);

    public void load(File inputFile,boolean save) throws IOException {
        BufferedReader reader =null;

        Set<com.rp.p2p.loan.BrowseLoansResultDao> browseLoansResultDaoSet =new HashSet<com.rp.p2p.loan.BrowseLoansResultDao>(
                Arrays.asList(new com.rp.p2p.loan.BrowseLoansResultDao[]{new com.rp.p2p.loan.db.BrowseLoansResultDao(),
                        //TODO new com.rp.p2p.loan.dynamo.BrowseLoansResultDao()
                }));
        try
        {
            reader = new BufferedReader(new FileReader(inputFile));
            String line;

            // skip first row
            reader.readLine();

            line= reader.readLine();
            String[] headers = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

            while ((line = reader.readLine()) != null)
            {
                String[] data =line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

                Map<String,String> record = build(headers, data);

                if (record == null) {
                    logger_.debug("The line [" + line + "] was ignored.");
                    return;
                }
                LoanListing loanListing = new LoanListing();
                loanListing.setCreditInfo(new CreditInfo());

                loanListing.setId(Long.valueOf(record.get("id")));
                loanListing.setMemberId(record.get("member_id"));
                loanListing.setLoanAmnt(Double.valueOf(record.get("loan_amnt")));
                loanListing.setFundedAmnt(Double.valueOf(record.get("funded_amnt")));
                //"funded_amnt_inv"
                loanListing.setTerm(Integer.valueOf(record.get("term").split(" ")[0]));
                loanListing.setIntRate(Double.valueOf(record.get("int_rate").substring(0,record.get("int_rate").length()-1)));
                loanListing.setInstallment(Double.valueOf(record.get("installment")));
                loanListing.setGrade(LoanGrade.valueOf(record.get("grade")));
                loanListing.setSubGrade(LoanSubGrade.valueOf(record.get("sub_grade").substring(0,1)+"_"+record.get("sub_grade").substring(1,2)));
                loanListing.setEmpTitle(record.get("emp_title"));
                loanListing.setEmpLength(parseEmpLength(record.get("emp_length")));
                loanListing.setHomeOwnership(HomeOwnership.valueOf(record.get("home_ownership")));
                loanListing.setAnnualInc(Double.valueOf(record.get("annual_inc")));
                loanListing.setIsIncV(IncomeVerification.valueOf(record.get("verification_status").replace(' ','_').toUpperCase()));
                loanListing.setListD(parseDate(record.get("issue_d")));
                loanListing.setReviewStatus(ReviewStatus.fromValue(record.get("loan_status").replace(' ','_').toUpperCase()));
                //"pymnt_plan"
                loanListing.setUrl(record.get("url"));
                loanListing.setDescription(record.get("desc").length()>255 ? record.get("desc").substring(0,254) : record.get("desc"));
                loanListing.setPurpose(LoanPurpose.valueOf(record.get("purpose").replace(' ','_').toUpperCase()));
                loanListing.setTitle(record.get("title"));
                //"zip_code"
                loanListing.setAddrState(record.get("addr_state"));

                loanListing.getCreditInfo().setDti(Double.valueOf(record.get("dti")));
                loanListing.getCreditInfo().setDelinq2Yrs(Integer.valueOf(record.get("delinq_2yrs")));
                loanListing.getCreditInfo().setEarliestCrLine(parseDate(record.get("earliest_cr_line")));
                loanListing.getCreditInfo().setFicoRangeLow(Integer.valueOf(record.get("fico_range_low")));
                loanListing.getCreditInfo().setFicoRangeHigh(Integer.valueOf(record.get("fico_range_high")));
                loanListing.getCreditInfo().setInqLast6Mths(Integer.valueOf(record.get("inq_last_6mths")));
                loanListing.getCreditInfo().setMthsSinceLastRecord(record.get("mths_since_last_record").length()!=0 ? Integer.valueOf(record.get("mths_since_last_record")):null);
                loanListing.getCreditInfo().setOpenAcc(Integer.valueOf(record.get("open_acc")));
                loanListing.getCreditInfo().setPubRec(Integer.valueOf(record.get("pub_rec")));
                loanListing.getCreditInfo().setRevolBal(Double.valueOf(record.get("revol_bal")));
                loanListing.getCreditInfo().setRevolUtil(record.get("revol_util").length()!=0?Double.valueOf(record.get("revol_util").substring(0,record.get("revol_util").length()-1)):null);
                loanListing.getCreditInfo().setTotalAcc(Integer.valueOf(record.get("total_acc")));
                loanListing.setInitialListStatus(record.get("initial_list_status"));
                //"out_prncp"
                //"out_prncp_inv"
                //"total_pymnt"
                //"total_pymnt_inv"
                //"total_rec_prncp"
                //"total_rec_int"
                //"total_rec_late_fee"
                //"recoveries"
                //"collection_recovery_fee"
                //"last_pymnt_d"
                //"last_pymnt_amnt"
                //"next_pymnt_d"
                //"last_credit_pull_d"
                //"last_fico_range_high"
                //"last_fico_range_low"
                //"collections_12_mths_ex_med"
                //"mths_since_last_major_derog"
                //"policy_code"
                //"application_type"
                loanListing.setAnnualInc(record.get("annual_inc").length()!= 0 ? Double.valueOf(record.get("annual_inc")):0.0);
                //"dti_joint"
                //"verification_status_joint"
                loanListing.getCreditInfo().setAccNowDelinq(Integer.valueOf(record.get("acc_now_delinq")));
                loanListing.getCreditInfo().setTotCollAmt(record.get("tot_coll_amt").length() != 0 ? Integer.valueOf(record.get("tot_coll_amt")):null);
                loanListing.getCreditInfo().setTotCurBal(record.get("tot_cur_bal").length()!=0?Integer.valueOf(record.get("tot_cur_bal")):0);
                loanListing.getCreditInfo().setOpenAcc(record.get("open_acc_6m").length()!=0?Integer.valueOf(record.get("open_acc_6m")):null);
                loanListing.getCreditInfo().setInqLast6Mths(record.get("open_il_6m").length()!=0?Integer.valueOf(record.get("open_il_6m")):null);
                //"open_il_12m"
                //"open_il_24m"
                loanListing.getCreditInfo().setMthsSinceRecentInq(record.get("mths_since_rcnt_il").length()!=0?Integer.valueOf(record.get("mths_since_rcnt_il")):null);
                //"total_bal_il"
                //"il_util"
                //"open_rv_12m"
                //"open_rv_24m"
                //"max_bal_bc"
                //"all_util"
                loanListing.getCreditInfo().setTotalRevHiLim(record.get("total_rev_hi_lim").length()!=0 ? Integer.valueOf(record.get("total_rev_hi_lim")):null);
                //"inq_fi"
                //"total_cu_tl"
                //"inq_last_12m"

                if (save)
                {
                    for (BrowseLoansResultDao browseLoansResultDao :browseLoansResultDaoSet)
                    {
                        try {
                            BrowseLoansResult browseLoansResult = new BrowseLoansResult();
                            browseLoansResult.getLoans().add(loanListing);
                            browseLoansResultDao.save(browseLoansResult);
                        }
                        catch (Exception ex)
                        {
                            logger_.warn("Unable to save loan ["+loanListing.getId()+"]",ex);
                        }
                    }
                }
            }
        }
        catch (FileNotFoundException fileNotFoundEx) {
            throw new RuntimeException(fileNotFoundEx);
        } catch (IOException e) {
            throw e;
        } finally
        {
            if (reader != null ) {
                try {
                    reader.close();
                }catch (Exception ex)
                { logger_.warn("Unable to close reader.  Logging exception and continuing",ex);}
            }
        }
    }

    private Date parseDate(String date)
    {
        try {
            return new SimpleDateFormat("MMM-yyyy").parse(date);
        } catch (ParseException e) {
            logger_.warn("Unable to parse date",e);
            return null;
        }
    }

    private Integer parseEmpLength(String empLength) {
        if(empLength.equals("n/a"))
        {
            return null;
        }
        else if (empLength.contains("<"))
        {
            return Integer.parseInt(empLength.split("\\s")[1]);
        }
        else
        {
            return Integer.parseInt(empLength.split("\\s")[0].split("\\+")[0]);
        }
    }

    private Map<String, String> build(String[] headers, String[] data) {
        if (headers.length != data.length) {
            logger_.warn("Header size [" + headers.length + "] is not the same as the data size [" + data.length + "]");
            return null;
        }
        Map<String,String> ret = new LinkedHashMap<String, String>();
        for (int i = 0 ; i < headers.length ; i++)
        {
            String cleanUpKey=headers[i].substring(1,headers[i].length()-1);

            String cleanUpValue=data[i];
            if (data[i].length()>0)
                cleanUpValue=data[i].substring(1,data[i].length()-1);
            ret.put(cleanUpKey.trim(),cleanUpValue.trim());
        }

        return ret;
    }


    public static void main(String args[]) throws IOException {
        if(args.length < 2)
            usage();
        else
        {
            String fileStr = args[0];
            boolean save = args.length==2? Boolean.parseBoolean(args[1]) : false;
            File inputFile = new File(fileStr);
            if (!inputFile.exists() || !inputFile.canRead())
            {
                logger_.info("Invalid file:"+inputFile.getAbsolutePath());
                return;
            }

            new LoanLoader().load(inputFile,save);
        }
    }

    private static void usage() {
        logger_.info(LoanLoader.class+" <inputCvsFile>");
    }
}
