package com.test.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		return "index";
	}
	
	@RequestMapping(value="/bank", method=RequestMethod.POST)
	public @ResponseBody Stack<HashMap<String, Object>> bank(HttpServletRequest request){
		request.getSession().invalidate();
		String account = request.getParameter("account");
		String id = request.getParameter("id").toUpperCase();
		String password = request.getParameter("password");
		Stack<HashMap<String, Object>> stack = new Stack<HashMap<String, Object>>();
		try {
			Calendar today = Calendar.getInstance();
			Calendar ago = Calendar.getInstance();
			ago.add(Calendar.YEAR, -1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Response response = Jsoup.connect("https://obank.kbstar.com/quics?asfilecode=524517")
					.method(Method.POST)
					.ignoreContentType(true)
					.data("계좌번호", account)
					.data("고객식별번호", id)
					.data("비밀번호", password)
					.data("빠른조회","Y")
					.data("응답방법","2")
					.data("조회구분","2")
					.data("USER_TYPE","02")
					.data("_FILE_NAME","KB_거래내역빠른조회.html")
					.data("_LANG_TYPE","KOR")
					.data("조회시작일",sdf.format(ago.getTime()))
					.data("조회종료일",sdf.format(today.getTime()))
					.execute();
			Document document = response.parse();
			Elements trs = document.select("tr[align]");
			for(Element tr: trs) {
				Elements tds = tr.getElementsByTag("td");
				HashMap<String, Object> transfer = new HashMap<String, Object>();
				transfer.put("transfer_date", tds.get(0).text());
				transfer.put("content",tds.get(1).text());
				transfer.put("name", tds.get(2).text());
				transfer.put("display", tds.get(3).text());
				transfer.put("output", tds.get(4).text().replaceAll(",", ""));
				transfer.put("input", tds.get(5).text().replaceAll(",", ""));
				transfer.put("remain", tds.get(6).text().replaceAll(",", ""));
				transfer.put("bank", tds.get(7).text());
				stack.push(transfer);
			}
			/*DB에 insert하기
			 * DB insert 할때는 중복을 피해야 한다는 것을 고려해야 한다.
			 * 그래서 모든 자료를 push하지 않고 우선 db에 있는지 체크를 먼저 하고 없으면 insert한다.
			 * 만약에 db에 이미 있는 자료라면 거기서 for문을 break 해주면 된다.
			 * if(service.select(transfer) == 0) stack.push(transfer);
			 * else break;
			 * 
			 * Stack을 사용한 이유는 오래된 내역을 먼저 DB에 넣기 위함
			 * while(stack.size()>0) service.insert(stack.pop()); // 이런식으로 하나씩 insert 해준다.
			 * */
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stack;
	}
}
