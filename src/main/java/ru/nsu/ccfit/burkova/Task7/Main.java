package ru.nsu.ccfit.burkova.Task7;

import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class Main {

	public static final String FILE_NAME = "output.xml";

	public static final String ELEM_RESPONSES = "responses";
	public static final String ELEM_RESPONSE = "response";
	public static final String ELEM_ID = "id";
	public static final String ELEM_STATUS = "status";
	public static final String ELEM_MESSAGE = "message";

	public static final int NUMBER_OF_ELEMENTS = 10;

	public static void main(String[] args) {

		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		IndentingXMLStreamWriter writer = null;

		try {
			writer = new IndentingXMLStreamWriter(factory.createXMLStreamWriter(new FileWriter(FILE_NAME)));
			writer.setIndentStep("    ");

			writer.writeStartDocument();
			writer.writeStartElement(ELEM_RESPONSES);


			Random random = new Random(System.currentTimeMillis());

			for (int i = 0; i < NUMBER_OF_ELEMENTS; ++i) {
				writer.writeStartElement(ELEM_RESPONSE);

				writer.writeAttribute(ELEM_ID, i + "");
				writer.writeComment("id: long");

				writer.writeStartElement(ELEM_STATUS);
				writer.writeCharacters(random.nextBoolean() ? "OK" : "ERROR");
				writer.writeEndElement();
				writer.writeComment("two possible values: OK or ERROR");

				writer.writeStartElement(ELEM_MESSAGE);
				writer.writeCharacters("Some text...");
				writer.writeEndElement();
				writer.writeComment("optional string message");

				writer.writeEndElement();
			}

			writer.writeEndElement();
			writer.writeEndDocument();

		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (XMLStreamException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
