package com.investment.app.services;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.investment.app.constants.InvestmentConstants;
import com.investment.app.entities.FNOReport;
import com.investment.app.http.GenericResponse;
import com.investment.app.repositories.FNOReportsRepository;

@Service
public class FNOReportsService {

	@Autowired
	private FNOReportsRepository reportRepo;
	
	

	public GenericResponse<?> addFnoEntry(FNOReport report) {
		
		final DecimalFormat df = new DecimalFormat("0.00");

		FNOReport savedReport = null;

		Double thirtyPerShare = (Double) (report.getGrossAmount() * 0.3);
		Double seventyPerShare = (Double) (report.getGrossAmount() * 0.7);

		Double thirtyPer = Double.parseDouble(df.format(thirtyPerShare));
		Double seventyPer = Double.parseDouble(df.format(seventyPerShare));
		
		try {
			if (report.getTradeResult().equals(InvestmentConstants.PROFIT)) {
				Double finalInHandAmount = seventyPer - report.getTradeCharges();
				report.setThirtyPercentShare(thirtyPer);
				report.setSeventyPercentShare(seventyPer);
				report.setNetAmount(Double.parseDouble(df.format(report.getGrossAmount() - report.getTradeCharges())));
				report.setFinalInHandProfit(Double.parseDouble(df.format(finalInHandAmount)));
			} else if (report.getTradeResult().equals(InvestmentConstants.LOSS)) {
				report.setThirtyPercentShare(thirtyPer);
				report.setSeventyPercentShare(seventyPer);
				report.setFinalInHandProfit(Double.parseDouble(df.format(seventyPer + report.getTradeCharges())));
				report.setNetAmount(Double.parseDouble(df.format(report.getGrossAmount() + report.getTradeCharges())));
			} else if (report.getTradeResult().equals(InvestmentConstants.NO_TRADE)) {
				report.setThirtyPercentShare(0.0);
				report.setSeventyPercentShare(0.0);
				report.setFinalInHandProfit(0.0);
				report.setNetAmount(0.0);
			}
		
			savedReport = reportRepo.save(report);

			if (savedReport == null) {
				return GenericResponse.builder().code("ERR").body(new FNOReport()).message("Error in Saving Report").build();
			}

			return GenericResponse.builder().code("OK").body(savedReport).message("Report Saved Successfully").build();

		} catch (Exception e) {
			return GenericResponse.builder().code("ERR").body(new FNOReport()).message("Something Went Wrong").build();
		}

	}

	public List<FNOReport> getAllFNOReports() {
		List<FNOReport> reportList = reportRepo.findAll();

		if (reportList == null) {
			reportList = new ArrayList<FNOReport>();
		}

		generateTotal(reportList);

		return reportList;
	}

	public void generateTotal(List<FNOReport> fnoReports) {

		final DecimalFormat df = new DecimalFormat("0.00");
		
		List<FNOReport> profitfnoReports = new ArrayList<FNOReport>();

		List<FNOReport> lossfnoReports = new ArrayList<FNOReport>();

		profitfnoReports.addAll(fnoReports.stream().filter(record -> record.getTradeResult().equals(InvestmentConstants.PROFIT)).collect(Collectors.toList()));

		lossfnoReports.addAll(fnoReports.stream().filter(record -> record.getTradeResult().equals(InvestmentConstants.LOSS)).collect(Collectors.toList()));

		Double totalGrossAmount = 0.0;
		Double totalTradeCharges = 0.0;
		Double totalNetAmount = 0.0;
		Double totalSeventyPerAmount = 0.0;
		Double totalthirtyPerAmount = 0.0;
		Double totalInHandProfit = 0.0;

		for (FNOReport fnoReport : profitfnoReports) {

			totalGrossAmount = totalGrossAmount + fnoReport.getGrossAmount();
			totalTradeCharges = totalTradeCharges + fnoReport.getTradeCharges();
			totalNetAmount = totalNetAmount + fnoReport.getNetAmount();
			totalSeventyPerAmount = totalSeventyPerAmount + fnoReport.getSeventyPercentShare();
			totalthirtyPerAmount = totalthirtyPerAmount + fnoReport.getThirtyPercentShare();
			totalInHandProfit = totalInHandProfit + fnoReport.getFinalInHandProfit();
		}

		for (FNOReport fnoReport : lossfnoReports) {
			totalGrossAmount = totalGrossAmount - fnoReport.getGrossAmount();
			totalTradeCharges = totalTradeCharges + fnoReport.getTradeCharges();
			totalNetAmount = totalNetAmount - fnoReport.getNetAmount();
			totalSeventyPerAmount = totalSeventyPerAmount - fnoReport.getSeventyPercentShare();
			totalthirtyPerAmount = totalthirtyPerAmount - fnoReport.getThirtyPercentShare();
			totalInHandProfit = totalInHandProfit - fnoReport.getFinalInHandProfit();
		}

		FNOReport totalFNOReport = new FNOReport();
		// totalFNOReport.setTradedDate(new LocalDate());
		totalFNOReport.setGrossAmount(Double.parseDouble(df.format(totalGrossAmount)));
		totalFNOReport.setTradeCharges(Double.parseDouble(df.format(totalTradeCharges)));
		totalFNOReport.setNetAmount(Double.parseDouble(df.format(totalNetAmount)));
		totalFNOReport.setSeventyPercentShare(Double.parseDouble(df.format(totalSeventyPerAmount)));
		totalFNOReport.setThirtyPercentShare(Double.parseDouble(df.format(totalthirtyPerAmount)));
		totalFNOReport.setFinalInHandProfit(Double.parseDouble(df.format(totalInHandProfit)));
		totalFNOReport.setTradeResult("N/A");

		fnoReports.add(totalFNOReport);
	}

	public List<FNOReport> getTradeByDateRange(LocalDate date1, LocalDate date2,String portfolioId) {
		List<FNOReport> reportList = reportRepo.findByTradedDateAndPortfolioId(date1, date2,portfolioId);
		generateTotal(reportList);
		return reportList;
	}

	public List<FNOReport> getByTradeResult(String result,String portfolioId) {
		List<FNOReport> repoList = reportRepo.findByTradeResultAndPortfolioId(result,portfolioId);
		generateTotal(repoList);
		return repoList;
	}

	public FNOReport getByTradedDate(LocalDate date,String portfolioId) {
		FNOReport report = reportRepo.findByTradedDateAndPortfolioId(date,portfolioId);
		if (report == null) {
			report = new FNOReport();
		}

		return report;
	}

	public List<FNOReport> getByTradedDateAndTradeResult(LocalDate startDate, String result,String portfolioId) {
		List<FNOReport> repoList = reportRepo.findByTradedDateAndTradeResultAndPortfolioId(startDate, result,portfolioId);
		generateTotal(repoList);
		return repoList;
	}

	public List<FNOReport> getByTradedDateRangeAndPortfolioIdAndTradeResult(LocalDate startDate, LocalDate endDate,String portfolioId, String result) {
		List<FNOReport> list = reportRepo.findByTradedDateAndPortfolioIdAndTradeResult(startDate, endDate,portfolioId, result);
		generateTotal(list);
		return list;
	}

}
