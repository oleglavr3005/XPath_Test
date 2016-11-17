import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XPath_Worker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse("inventory.xml");

			// Create XPathFactory object
			XPathFactory xpathFactory = XPathFactory.newInstance();

			// Create XPath object
			XPath xpath = xpathFactory.newXPath();

			String name = getAuthorNameById(doc, xpath, "myfave");
			System.out.println("Author of book with id \"fave\": " + name);

			List<String> names = getBooksPricesByStyle(doc, xpath, "autobiography");
			System.out.println("Prices of books with style 'autobiography' are:" + Arrays.toString(names.toArray()));

			List<String> femaleEmps = getMagazineStyles(doc, xpath);
			System.out.println("Styles of magazines:" + Arrays.toString(femaleEmps.toArray()));

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

	}

	private static List<String> getMagazineStyles(Document doc, XPath xpath) {
		List<String> list = new ArrayList<>();
		try {
			// create XPathExpression object
			XPathExpression expr = xpath.compile("//magazine/@style");
			// evaluate expression result on XML document
			NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++)
				list.add(nodes.item(i).getNodeValue());
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return list;
	}

	private static List<String> getBooksPricesByStyle(Document doc, XPath xpath, String style) {
		List<String> list = new ArrayList<>();
		try {
			XPathExpression expr0 = xpath.compile("//book[@style=\"" + style + "\"]/price/text()");
			NodeList nodes = (NodeList) (expr0.evaluate(doc, XPathConstants.NODESET));
			for (int i = 0; i < nodes.getLength(); i++)
				list.add(nodes.item(i).getNodeValue());
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return list;
	}

	private static String getAuthorNameById(Document doc, XPath xpath, String id) {
		String name = null;
		try {
			XPathExpression expr0 = xpath.compile("//book[@id=\"" + id + "\"]/author/first-name/text()");
			XPathExpression expr1 = xpath.compile("//book[@id=\"" + id + "\"]/author/last-name/text()");
			name = (String) expr0.evaluate(doc, XPathConstants.STRING)+" "+(String) expr1.evaluate(doc, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return name;
	}

}
