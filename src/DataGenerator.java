import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DataGenerator {
	// Instance variables 
	private int numToGenerate;
	private String output;
	private int currentId;
	private DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
	private TransformerFactory transformerFactory = TransformerFactory.newInstance();
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	private int counter = 0;
	private int rand = getRandomNumber(1,300);

	public static void main(String[] args)throws IOException {
		Scanner usr = new Scanner(System.in);
		int numberToGenerate = getNumberToGenerate(usr);
		String output = getOutput(usr);
		DataGenerator dataGen = new DataGenerator(numberToGenerate, output);
		dataGen.generate();
		System.out.println("Done");

	}
	private static int getNumberToGenerate(Scanner scanner){
		System.out.println("How many publications would you like to generate");
		int numToGenerate = scanner.nextInt();
		return numToGenerate;

	}

	private static String getOutput(Scanner scanner){
		String output = null;
		do {
			System.out.println("Enter path to out out directory");
			output = scanner.nextLine();
		} while (!isVal(output));

		return output;

	}

	private static boolean isVal(String output){
		File file = new File(output);
		return file.isDirectory();
	}

	public DataGenerator(int numToGenerate, String output){
		this.numToGenerate = numToGenerate;
		this.output = output;
	}

	public void generate(){
		for(int i = 0; i < numToGenerate; i++){
			generateId();
			generatePublicationMetaData();
			generatePublicationFullText();
			generatePublicationImage();

		}
	}

	private void generateId(){
		currentId ++;
	}

	private void generatePublicationMetaData()
	{
		try {
			DocumentBuilder documentBuilder =  documentFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			addPublication(document);
			File publicationDir = new File(output + File.separator + "publication-" + currentId);
			publicationDir.mkdirs();
			File outputFile =  new File(publicationDir.getAbsolutePath() + File.separator + "publication.xml");
			writeDocumentToFile(document,outputFile);

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void generatePublicationFullText(){
		try {
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			addPages(document);
			File publicationDir = new File(output + File.separator + "publication-" + currentId);
			publicationDir.mkdirs();
			File outputFile =  new File(publicationDir.getAbsolutePath() + File.separator + "fullText.xml");
			writeDocumentToFile(document,outputFile);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void generatePublicationImage(){

		File publicationDir = new File(output + File.separator + "publication-" + currentId + File.separator + "Images" );
		publicationDir.mkdirs();
		File outputFile =  new File(publicationDir.getAbsolutePath() + File.separator + currentId + ".tif");
		try {
			outputFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	////// publication block //////
	public void addPublication(Document document){

		Element publication = document.createElement("publication");
		document.appendChild(publication);
		addIdentifier(document, publication);
		addAuthors(document, publication);
		addTitle(document, publication);
		addSubjects(document, publication);

	}


	public void addIdentifier(Document document, Element publication){

		Element identifier = document.createElement("identifier");
		publication.appendChild(identifier);
		identifier.appendChild(document.createTextNode(Integer.toString(currentId)));
	}

	public void addAuthors(Document document, Element publication){

		Element authors = document.createElement("authors");
		publication.appendChild(authors);

		int numberOfAuthors = getRandomNumber(1,5);
		for(int i = 0; i < numberOfAuthors; i ++){
			addAuthor(document, authors);

		}

	}

	public void addAuthor(Document document, Element authors){

		Element author = document.createElement("author");
		authors.appendChild(author);


		Element authorname = document.createElement("authorname");
		authorname.appendChild(document.createTextNode(getRandomText(getRandomNumber(1,10))));
		author.appendChild(authorname);

		Element dob = document.createElement("dob");
		String date = dateFormat.format(getRandomDate());
		dob.appendChild(document.createTextNode(date));
		author.appendChild(dob);
	}

	public void addTitle(Document document, Element publication){

		Element title = document.createElement("title");
		publication.appendChild(title);

		Element longTitle = document.createElement("longTitle");
		longTitle.appendChild(document.createTextNode(getRandomText(getRandomNumber(1,30))));
		title.appendChild(longTitle);

		Element shortTitle = document.createElement("shortTitle");
		shortTitle.appendChild(document.createTextNode(getRandomText(getRandomNumber(1,10))));
		title.appendChild(shortTitle);
	}

	public void addSubjects(Document document, Element publication){

		Element subjects = document.createElement("subjects");
		publication.appendChild(subjects);

		int numberOfSubjects = getRandomNumber(1, 30);
		for(int i = 0; i < numberOfSubjects; i++ ){
			addSubject(document,subjects);
		}

	} 

	public void addSubject(Document document, Element subjects){

		Element subject = document.createElement("subject");
		subject.appendChild(document.createTextNode(getRandomText(getRandomNumber(1,10))));
		subjects.appendChild(subject);

		Element subject1 = document.createElement("subject");
		subject1.appendChild(document.createTextNode(getRandomText(getRandomNumber(1,10))));
		subjects.appendChild(subject1);

		Element subject2 = document.createElement("subject");
		subject2.appendChild(document.createTextNode(getRandomText(getRandomNumber(1,10))));
		subjects.appendChild(subject2);
	}




	/////add full text.xml

	public void addPages(Document document){

		Element pages = document.createElement("pages");
		document.appendChild(pages);
		addPage(document, pages);

		int random = getRandomNumber(1, 20);
		for(int i=0; i<random; i++){

			addPage(document, pages);
			addText(document, pages);
		}
	}

	public void addPage(Document document, Element pages){

		Element page = document.createElement("page");
		pages.appendChild(page);
		addText(document, page);


		int randomPage = getRandomNumber(1, 20);
		counter++;
		rand++;
		for(int i = 0; i < randomPage; i++){

			addPageAttributes(document, page);
		}

	}

	public void addText(Document document, Element page){

		Element text = document.createElement("text");
		page.appendChild(text);

		int numberOfText = getRandomNumber(1, 20);
		for(int i = 0; i < numberOfText; i++){
			addTextAttributes(document, text);

		}

	}

	public void addTextAttributes(Document document, Element text){

		Element word =  document.createElement("word");
		word.appendChild(document.createTextNode(getRandomText(10)));
		text.appendChild(word);

		Attr coords = document.createAttribute("coords");
		coords.setValue(getRandomCoords());
		word.setAttributeNode(coords);
	}

	public void addPageAttributes(Document document, Element page){


		Attr sequenceAtt = document.createAttribute("sequence");
		sequenceAtt.setValue(Integer.toString(counter));
		page.setAttributeNode(sequenceAtt);

		Attr numberAtt = document.createAttribute("number");
		numberAtt.setValue(Integer.toString(rand));
		page.setAttributeNode(numberAtt);

	}
	

	////// Random block /////////////////
	public int getRandomNumber(int min, int max){
		int randomNumber = (int) (Math.random() * (max +1 -min)) + min;
		return randomNumber;
	}

	public char getRandomChar(){
		int number = getRandomNumber(33, 126);
		char randomChar = (char) number;
		return randomChar;
	}

	public String getRandomText(int length){
		StringBuilder randomText = new StringBuilder();
		for(int i = 0; i < length; i++){
			randomText.append(getRandomChar());
		}
		return randomText.toString();
	}

	public Date getRandomDate(){
		int day = getRandomNumber(1, 31);
		int month = getRandomNumber(1,12);
		int year = getRandomNumber(1000, 2000);
		try {
			Date date = dateFormat.parse(day + "-" + month + "-" + year);
			return date;
		} catch (ParseException e) {
			return null;

		}
	}

	public String getRandomCoords()
	{
		int coord1 = getRandomNumber(1,300);
		int coord2 = getRandomNumber(1,300);
		int coord3 = getRandomNumber(1,300);
		int coord4 = getRandomNumber(1,300);
		return coord1 + "," + coord2 + "," + coord3 + "," + coord4;
	}


	private void writeDocumentToFile(Document document, File outPutFile){
		try {
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(outPutFile);
			transformer.transform(source, result);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}