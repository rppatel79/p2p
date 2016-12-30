
var VALID_HOMEOWNERSHIP = [com.rp.p2p.model.HomeOwnership.RENT, com.rp.p2p.model.HomeOwnership.MORTGAGE, com.rp.p2p.model.HomeOwnership.OWN];
var VALID_INCOME_VERIFICATION = [com.rp.p2p.model.IncomeVerification.SOURCE_VERIFIED,com.rp.p2p.model.IncomeVerification.VERIFIED];
var VALID_LOAN_PURPOSE = [com.rp.p2p.model.LoanPurpose.CREDIT_CARD,com.rp.p2p.model.LoanPurpose.DEBT_CONSOLIDATION,com.rp.p2p.model.LoanPurpose.HOME_IMPROVEMENT];//Is this okay?
var VALID_GRADE = [com.rp.p2p.model.LoanGrade.A,com.rp.p2p.model.LoanGrade.B,com.rp.p2p.model.LoanGrade.C];
var VALID_TERM = [36];

var brokenRules =0;
if( ((loan.getLoanAmnt() / loan.getAnnualInc())*100.0) > 50.0 )
{
	logger_.info("Failed ((loan.getLoanAmnt()/loan.getAnnualInc())*100.0) [" +((loan.getLoanAmnt() / loan.getAnnualInc())*100.0) +"] " + loan.getId());
	brokenRules++;
}
if( (loan.getAnnualInc()) < 43500.0 )
{
	logger_.info("Failed (loan.getAnnualInc()) [" +loan.getAnnualInc() +"] " + loan.getId());
	brokenRules++;
}
if (VALID_TERM.indexOf(loan.getTerm())<0)
{
	logger_.info("Failed VALID_TERM [" +loan.getTerm()+ "] " + loan.getId());
	brokenRules++;
}
if (!loan.getEmpTitle().toUpperCase().includes("PASTOR") )
{
	logger_.info("Failed loan.getEmpTitle() ["+loan.getEmpTitle() + "] " + loan.getId());
	brokenRules++;
}
if (loan.getCreditInfo().getDti() > 21.0)
{
	logger_.info("Failed loan.getCreditInfo().getDti() [" +loan.getCreditInfo().getDti() + "] " + loan.getId());
	brokenRules++;
}
if (loan.getCreditInfo().getPubRec() != null && loan.getCreditInfo().getPubRec() > 0)
{
	logger_.info("Failed loan.getCreditInfo().getPubRec() [" +loan.getCreditInfo().getPubRec()+ "] " + loan.getId());
	brokenRules++;
}
if (loan.getLoanAmnt() > 40000)
{
	logger_.info("Failed loan.getLoanAmnt() [" +loan.getLoanAmnt()+ "] " + loan.getId());
	brokenRules++;
}
if((loan.getIntRate() < 9.7))
{
	logger_.info("Failed INT_RATE [" + loan.getIntRate()+"] " + loan.getId());
	brokenRules++;
}
if((loan.getIntRate() +5.0 )< (loan.getExpDefaultRate()+loan.getServiceFeeRate()))//MIN_INT_RATE
{
	logger_.info("Failed MIN_INT_RATE [" + loan.getIntRate()+"] " + loan.getId());
	brokenRules++;
}
if (allInvestedLoans.contains(new java.lang.Long(loan.getId())))//unsure why I need to wrap this as a Long, but if I don't rhino is converting to a double
{
	logger_.info("Failed allInvestedLoans.contains(loan.getId())" + " " + loan.getId());
	brokenRules++;
}
if (brokenRules > 0)
{
	logger_.info("Loan ["+loan.getId()+"] broke ["+brokenRules+"] rules");
}
else
{
	logger_.info("Loan [" + loan.getId() + "] succeeded");
}
brokenRules;