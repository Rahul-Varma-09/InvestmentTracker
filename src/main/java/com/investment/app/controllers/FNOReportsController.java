package com.investment.app.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.investment.app.entities.FNOReport;
import com.investment.app.http.GenericResponse;
import com.investment.app.services.FNOReportsService;

@RestController
@RequestMapping("/fno")
@CrossOrigin(allowedHeaders = "*")
public class FNOReportsController {
	
	@Autowired private FNOReportsService fnoReportService;
	
	@PostMapping("/addEntry")
	public GenericResponse<?> addFnoEntry(@RequestBody FNOReport report) {
		return fnoReportService.addFnoEntry(report);
	}
	
	@GetMapping("/getAll")
	public List<FNOReport> getAllFNOReports(){
	    return fnoReportService.getAllFNOReports();
	}

	@GetMapping("/getByTradedDate/{date1}/{date2}")
	public List<FNOReport> getByTradeDate(@PathVariable("date1")String date1,@PathVariable("date2")String date2){
		LocalDate startDate = LocalDate.parse(date1, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate endDate = LocalDate.parse(date2, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		return fnoReportService.getTradeByDateRange(startDate,endDate);
	}
	
	@GetMapping("/getByTradeResult/{result}")
	public List<FNOReport> getByTradeResult(@PathVariable("result")String result){
		return fnoReportService.getByTradeResult(result);
	}
	
	@GetMapping("/getByTradedDate/{date}")
	public List<FNOReport> getByTradedDate(@PathVariable("date")String date){
		
		LocalDate startDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		List<FNOReport> reportList = new ArrayList<FNOReport>(); 
	
		reportList.add(fnoReportService.getByTradedDate(startDate));
		
		return reportList;
		
	}
	
	
	@GetMapping("/getByDateAndResult/{date}/{result}")
	public List<FNOReport> getDataByTradedDateAndResult(@PathVariable("date") String tradedDate,@PathVariable("result") String result) {
		LocalDate startDate = LocalDate.parse(tradedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		return fnoReportService.getByTradedDateAndTradeResult(startDate,result);
		
	}
	
	@GetMapping("/getByDateRangeAndResult/{date1}/{date2}/{result}")
	public List<FNOReport> getByTradedDateRangeAndTradeResult(@PathVariable("date1")String date1,@PathVariable("date2")String date2,@PathVariable("result") String result){
		
		LocalDate startDate = LocalDate.parse(date1, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		LocalDate endDate = LocalDate.parse(date2, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	
		return fnoReportService.getByTradedDateRangeAndTradeResult(startDate, endDate ,result);
	}
	
	
}
