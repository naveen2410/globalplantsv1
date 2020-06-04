/**
 *  Copyright 2005-2016 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package globalmanufacturing_v1.globalmanufacturing_v1;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

	Production production = new Production();

	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //2019-04-26T07:00:00
	Date date = new Date();
		
	@GetMapping
	public String index() {
		return "redirect:/welcome";
	}

	@GetMapping("/welcome")
	public String welcomeForm(Model model) {

		model.addAttribute("production", production);
		
		return "welcome";
	}

	/*
	 * @PostMapping("/welcomeProd") public String prodWelcomeForm(Model model) {
	 * 
	 * Calendar c = Calendar.getInstance(); c.setTime(date);
	 * 
	 * c.add(Calendar.DATE, -1);
	 * 
	 * Date prevdate = c.getTime();
	 * 
	 * production.setFromdate(sdf.format(prevdate)); production.setFromHH("00");
	 * production.setFromMM("00"); production.setFromSS("00");
	 * 
	 * model.addAttribute("production", production);
	 * 
	 * List<SiteList> lstSiteLists = new ArrayList<SiteList>();
	 * 
	 * SiteDetails sitedetails = new SiteDetails(); try {
	 * sitedetails.getSiteList(lstSiteLists); } catch (IOException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * 
	 * model.addAttribute("sitelists", lstSiteLists);
	 * 
	 * return "production";
	 * 
	 * }
	 */
	
	
	  @PostMapping("/welcomeSel") 
	  public String selectionForm(Model model) {
	  
		 Calendar c = Calendar.getInstance(); c.setTime(date);
		 c.add(Calendar.DATE, -1);
		 
		 Date prevdate = c.getTime();
		 
		 production.setFromdate(sdf.format(prevdate)); production.setFromHH("00");
		 production.setFromMM("00"); production.setFromSS("00");
		 
		 model.addAttribute("production", production);
		 
		 List<SiteList> lstSiteLists = new ArrayList<SiteList>();
		 
		 SiteDetails sitedetails = new SiteDetails(); 
		 try {
		 sitedetails.getSiteList(lstSiteLists); 
		 } catch (IOException e) 
		 { 
			 e.printStackTrace(); 
		 }
		 
		 model.addAttribute("sitelists", lstSiteLists);
		  
		 return "selection"; 
	  
	  }
	 

	@PostMapping("/selection") 
	public String selectSubmit(Model model, @ModelAttribute Production production) {

		String siteAInput = "PPME";
		String siteBInput = "EXID";

		
		/*
		 * String prefixShopOrderBO_A = "ShopOrderBO:"+siteAInput+","+selectedOrderA;
		 * String prefixShopOrderBO_B = "ShopOrderBO:"+siteAInput+","+selectedOrderA;
		 */

		String fromDt = production.getFromdate();
		String fromHours = production.getFromHH();
		String fromMins = production.getFromMM();
		String fromSeconds = production.getFromSS();
		
		String fromDateTime = fromDt + "T" + fromHours +":"+fromMins+":"+fromSeconds;
		
		//String fromDateTime = "2019-05-05T00:00:00";


		return "welcome";
	}
	
	
	@PostMapping("/api/search/{sitelist}")
    public ResponseEntity<?> getProductionResultViaAjax(@PathVariable("sitelist") String sitelist) {

      
        String jsStringResult = "{\"sites\":[ \""+sitelist.split(",")[0]+"\", \""+sitelist.split(",")[1]+"\"],\r\n" + 
        		"\"qty\": [{\"y\": 40,\"color\": \"red\",\"drilldown\": {\"categories\": [\"order 1\",\"order 2\",\"order 3\",\"order 4\"], \"series\": [{\"type\": \"column\",\"name\": \"Target\",\"data\": [10.85, 7.35, 33.06, 2.81], \"color\": \"blue\"},{\"type\": \"column\",\"name\": \"Actual\",\"data\": [1, 5, 10, 15], \"color\": \"green\"}]}},{\"y\": 50,\"color\": \"red\",\"drilldown\": {\"categories\": [\"order 5\",\"order 6\",\"order 7\"], \"series\": [{\"type\": \"column\",\"name\": \"Target\",\"data\": [20.85, 17.35, 36.06], \"color\": \"blue\"},{\"type\": \"column\",\"name\": \"Actual\",\"data\": [1, 5, 10], \"color\": \"green\"}]}}]\r\n" + 
        		"}";
        
        
        String jsonStringResult = "["
                +"{\"y\": 40,\"color\": \"red\",\"drilldown\": {\"categories\": [\"order 1\",\"order 2\",\"order 3\",\"order 4\"], \"series\": [{\"type\": \"column\",\"name\": \"Target\",\"data\": [10.85, 7.35, 33.06, 2.81], \"color\": \"blue\"},{\"type\": \"column\",\"name\": \"Actual\",\"data\": [1, 5, 10, 15], \"color\": \"green\"}]}}"
                + ",{\"y\": 50,\"color\": \"red\",\"drilldown\": {\"categories\": [\"order 5\",\"order 6\",\"order 7\"], \"series\": [{\"type\": \"column\",\"name\": \"Target\",\"data\": [20.85, 17.35, 36.06], \"color\": \"blue\"},{\"type\": \"column\",\"name\": \"Actual\",\"data\": [1, 5, 10], \"color\": \"green\"}]}}"
                + "]";
        
      
                
        return ResponseEntity.ok(jsStringResult);

   }
	
	
	@PostMapping("/api/searchRes/{sitelist}")
    public ResponseEntity<?> getResourceResultViaAjax(@PathVariable("sitelist") String sitelist) {

      
        String jsStringResult = "{\"sites\":[ \""+sitelist.split(",")[0]+"\", \""+sitelist.split(",")[1]+"\",\""+sitelist.split(",")[2]+"\"],\r\n" + 
        		"\"qty\": [{\"y\": 40,\"color\": \"blue\"},{\"y\": 50,\"color\": \"green\"},{\"y\":65,\"color\":\"red\"}]"+          				
        		"}";
        
        
        String jsonStringResult = "["
                +"{\"y\": 40,\"color\": \"red\",\"drilldown\": {\"categories\": [\"order 1\",\"order 2\",\"order 3\",\"order 4\"], \"series\": [{\"type\": \"column\",\"name\": \"Target\",\"data\": [10.85, 7.35, 33.06, 2.81], \"color\": \"blue\"},{\"type\": \"column\",\"name\": \"Actual\",\"data\": [1, 5, 10, 15], \"color\": \"green\"}]}}"
                + ",{\"y\": 50,\"color\": \"red\",\"drilldown\": {\"categories\": [\"order 5\",\"order 6\",\"order 7\"], \"series\": [{\"type\": \"column\",\"name\": \"Target\",\"data\": [20.85, 17.35, 36.06], \"color\": \"blue\"},{\"type\": \"column\",\"name\": \"Actual\",\"data\": [1, 5, 10], \"color\": \"green\"}]}}"
                + "]";
        
      
                
        return ResponseEntity.ok(jsStringResult);

   }
	
	@PostMapping("/welcomeProd/{sitelist}")
	public String prodSubmit(Model model, @ModelAttribute Production production, @PathVariable("sitelist") String sitelist) {

		List<SiteOrder> listSiteOrderAs = new ArrayList<SiteOrder>();
		List<SiteOrder> listSiteOrderBs = new ArrayList<SiteOrder>();
		
		List<OrderSFC> listOrderSfcAs = new ArrayList<OrderSFC>();
		List<OrderSFC> listOrderSfcBs = new ArrayList<OrderSFC>();

		//String siteAInput = "PPME";
		//String siteBInput = "EXID";
		
		String siteAInput = sitelist.split(",")[0];
		String siteBInput = sitelist.split(",")[1];
				
		System.out.println("siteAInput : " + siteAInput + " siteBInput : " + siteBInput);
		
		String selectedOrderA = "PLATETRT005";
		String selectedOrderB = "1000486";
		
		String orderQtyCSV_A = "";
		String orderQtyCSV_B = "";
		
		
		/*
		 * String prefixShopOrderBO_A = "ShopOrderBO:"+siteAInput+","+selectedOrderA;
		 * String prefixShopOrderBO_B = "ShopOrderBO:"+siteAInput+","+selectedOrderA;
		 */

		String fromDt = production.getFromdate();
		String fromHours = production.getFromHH();
		String fromMins = production.getFromMM();
		String fromSeconds = production.getFromSS();
		
		//String fromDateTime = fromDt + "T" + fromHours +":"+fromMins+":"+fromSeconds;
		
		String fromDateTime = "2019-05-05T00:00:00";
		
		/*
		 * model.addAttribute("fromDateObj",fromDateTime);
		 * model.addAttribute("siteListObj",production.getSitelist());
		 */

		ShowProdAnalysisDetails showprodanalysisdata = new ShowProdAnalysisDetails();

		try {
			orderQtyCSV_A = showprodanalysisdata.displayOrderQtyData(siteAInput,fromDateTime);
			orderQtyCSV_B = showprodanalysisdata.displayOrderQtyData(siteBInput,fromDateTime);
			
			showprodanalysisdata.displaySiteOrder(listSiteOrderAs, siteAInput,fromDateTime);
			showprodanalysisdata.displaySiteOrder(listSiteOrderBs, siteBInput,fromDateTime);
			
			showprodanalysisdata.displayOrderSFC(listOrderSfcAs, siteAInput, fromDateTime, selectedOrderA);
			showprodanalysisdata.displayOrderSFC(listOrderSfcBs, siteBInput, fromDateTime, selectedOrderB);
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		  String arrA[] = orderQtyCSV_A.split(","); 
		  String arrB[] = orderQtyCSV_B.split(",");
		  
		  Map<String,Integer> barChartData_A = new HashMap<>(); Map<String,Integer>
		  barChartData_B = new HashMap<>();
		  
		  Map<String,Integer> eachOrderActualBarData_A = new HashMap<>();
		  Map<String,Integer> eachOrderTargetBarData_A = new HashMap<>();
		  
		  Map<String,Integer> eachOrderActualBarData_B = new HashMap<>();
		  Map<String,Integer> eachOrderTargetBarData_B = new HashMap<>();
		  
		  barChartData_A.put("Target",Integer.parseInt(arrA[0].split("\\.")[0]));
		  barChartData_A.put("Actual",Integer.parseInt(arrA[1].split("\\.")[0]));
		  
		  barChartData_B.put("Target",Integer.parseInt(arrB[0].split("\\.")[0]));
		  barChartData_B.put("Actual",Integer.parseInt(arrB[1].split("\\.")[0]));
		  
		  for(int i=0; i<listSiteOrderAs.size(); i++) {
		  eachOrderActualBarData_A.put(listSiteOrderAs.get(i).getOrder(),
		  Integer.parseInt(listSiteOrderAs.get(i).getYieldqty().split("\\.")[0]));
		  eachOrderTargetBarData_A.put(listSiteOrderAs.get(i).getOrder(),
		  Integer.parseInt(listSiteOrderAs.get(i).getBuildqty().split("\\.")[0])); }
		  
		  for(int i=0; i<listSiteOrderBs.size(); i++) {
		  eachOrderActualBarData_B.put(listSiteOrderBs.get(i).getOrder(),
		  Integer.parseInt(listSiteOrderBs.get(i).getYieldqty().split("\\.")[0]));
		  eachOrderTargetBarData_B.put(listSiteOrderBs.get(i).getOrder(),
		  Integer.parseInt(listSiteOrderBs.get(i).getBuildqty().split("\\.")[0])); }
		  
		  model.addAttribute("barChartOrderQtyDataA",barChartData_A);
		  model.addAttribute("barChartOrderQtyDataB",barChartData_B);
		  
		  model.addAttribute("eachOrderActualQtyBarChartA",eachOrderActualBarData_A);
		  model.addAttribute("eachOrderTargetQtyBarChartA",eachOrderTargetBarData_A);
		  model.addAttribute("eachOrderActualQtyBarChartB",eachOrderActualBarData_B);
		  model.addAttribute("eachOrderTargetQtyBarChartB",eachOrderTargetBarData_B);
		  
		  model.addAttribute("siteorder_as", listSiteOrderAs);
		  model.addAttribute("siteorder_bs", listSiteOrderBs);
		  
		  model.addAttribute("ordersfc_as", listOrderSfcAs);
		  model.addAttribute("ordersfc_bs", listOrderSfcBs);
		 

		//return "prod_analysis";
		return "prod_analysis_new";
	}

	/* @PostMapping("/resource") */
	
	@PostMapping("/welcomeRes")
	public String resSubmit(Model model) {

		
		String siteInputA = "PPME";
		String siteInputB = "EXID";
		
		List<WCPerformance> listWCPerfAs = new ArrayList<WCPerformance>();
		List<WCPerformance> listWCPerfBs = new ArrayList<WCPerformance>();
		
		String resourceA = "ResourceBO:PPME,R1";
		String resourceB = "ResourceBO:EXID,RES1-1";
		
		String reasonCodeCSV_A = "";
		String reasonCodeCSV_B = "";
		
		
		String selectedSiteResList = "PPME - R1,EXID - RES1-1";
		
		ShowResAnalysisDetails showresanalysisdata = new ShowResAnalysisDetails();

		try {
			showresanalysisdata.getLinePerformance(listWCPerfAs,siteInputA);
			showresanalysisdata.getLinePerformance(listWCPerfBs,siteInputB);
			
			reasonCodeCSV_A = showresanalysisdata.getReasonCodesDuration(resourceA);
			reasonCodeCSV_B = showresanalysisdata.getReasonCodesDuration(resourceB);
									
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<SiteList> lstSiteLists = new ArrayList<SiteList>();
		 
		 SiteDetails sitedetails = new SiteDetails(); 
		 try {
			 sitedetails.getSiteList(lstSiteLists); 
		 } 
		 catch (IOException e) 
		 {
			 e.printStackTrace(); 
		 }
		 
		

		String resarrA[] = reasonCodeCSV_A.split(",");
		String resarrB[] = reasonCodeCSV_B.split(",");

		String siteResList[] = selectedSiteResList.split(",");

		model.addAttribute("selectedSiteResA", siteResList[0]);
		model.addAttribute("selectedSiteResB", siteResList[1]);

		Map<String,Integer> pieChartWCData_A = new HashMap<>();
		Map<String,Integer> pieChartWCData_B = new HashMap<>();
		
		for(int i=0; i<listWCPerfAs.size(); i++)
		{
			pieChartWCData_A.put(listWCPerfAs.get(i).getWc(), Integer.parseInt(listWCPerfAs.get(i).getPerf().split("\\.")[0]));
		}

		for(int i=0; i<listWCPerfBs.size(); i++)
		{
			pieChartWCData_B.put(listWCPerfBs.get(i).getWc(), Integer.parseInt(listWCPerfBs.get(i).getPerf().split("\\.")[0]));
		}


		Map<String,Integer> pieChartResData_A = new HashMap<>();
		Map<String,Integer> pieChartResData_B = new HashMap<>();
		
		for(int i=0; i<resarrA.length; i++) {
			pieChartResData_A.put("ReasonCode_"+(i+1),Integer.parseInt(resarrA[i].split("\\.")[0]));
		}

		for(int i=0; i<resarrB.length; i++) {
			pieChartResData_B.put("ReasonCode_"+(i+1),Integer.parseInt(resarrB[i].split("\\.")[0]));
		}
		
		model.addAttribute("selectedSite_A",siteInputA);
		model.addAttribute("selectedSite_B",siteInputB);
		
		model.addAttribute("pieChartWCData_A",pieChartWCData_A);
		model.addAttribute("pieChartWCData_B",pieChartWCData_B);
		
		model.addAttribute("sitelists", lstSiteLists);

		model.addAttribute("pieChartResData_A",pieChartResData_A);
		model.addAttribute("pieChartResData_B",pieChartResData_B);
    
		
		//return "res_analysis_demo";
		//return "res_analysis";
		return "res_analysis_new";
		//return "res_analysis_tree";
		//return "res_tree";
	}
}
