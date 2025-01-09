package sist.action;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import sist.vo.DataVO;

public class IndexAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		//공공데이터를 호출하는 url경로를
		StringBuffer sb= new StringBuffer("http://apis.data.go.kr/B551011/KorService1/searchFestival1?");
		sb.append("serviceKey=2ym8cTgVmYNsi00U9qVX3TECWNbufs3ZdpZhuNCpLZBOOwyuVjYLQbNCJqNJ%2B0dtYleQJhGnxmZjn01IPsW7NA%3D%3D");
		sb.append("&numOfRows=10");
		sb.append("&pageNo=1");
		sb.append("&MobileOS=ETC");
		sb.append("&MobileApp=AppTest");
		sb.append("&arrange=A");
		sb.append("&listYN=Y");
		sb.append("&eventStartDate=20250101");
		sb.append("&areaCode=1");
		
		//부라우저에창에서 경로를 입력하고 요청하듯이 프로그램 상에서는 요청할때는 url 객체를 만들어야 한다
		try {
			URL url = new URL(sb.toString());
		//경로를 연결하는 객체
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		
		//응답 받을 데이터의 형식을 지정
		conn.setRequestProperty("Content-Type", "application/xml");
		//연결 요청
		conn.connect();
		
		//JDOM 라이브러리에 있는 SAXBuilder를 통해 응답메시지를 xml문서화 시키기 위해
	
		//필요로하는 객체이다
		
		SAXBuilder builder = new SAXBuilder();
		
		//응답 되는 내용을 
		Document doc = builder.build(conn.getInputStream());
		
		//루트엘리먼트를 얻어내자
		Element root=doc.getRootElement();
		System.out.println(root.getName()); //response
		Element body = root.getChild("body");
		Element items = body.getChild("items");
		List<Element> item_list = items.getChildren("item");
		
		DataVO[] ar = new DataVO[item_list.size()];
		int i=0;
		for(Element item: item_list) {
			String title = item.getChildText("title");
			String eventstartdate = item.getChildText("eventstartdate");
			String eventenddate = item.getChildText("eventenddate");
			String firstimage = item.getChildText("firstimage");
			String secondimage2 = item.getChildText("secondimage2");
			String mapx = item.getChildText("mapx");
			String mapy = item.getChildText("mapy");
			String addr1 = item.getChildText("addr1");
			String addr2 = item.getChildText("addr2");
			String tel = item.getChildText("tel");
			
		
			
			
			
			//vo객체로 생성
			DataVO vo =new DataVO(title, mapx,  mapy,  addr1,  addr2,firstimage,
					secondimage2, tel, eventstartdate, eventenddate);
			ar[i++] = vo;
		}
		request.setAttribute("ar", ar);
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/index.jsp";
	}

}
