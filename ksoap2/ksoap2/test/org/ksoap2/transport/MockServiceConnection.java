/* Copyright (c) 2006, James Seigel, Calgary, AB., Canada
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The  above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE. */

package org.ksoap2.transport;

import java.io.*;
import java.net.*;

import org.ksoap2.transport.mock.*;

public class MockServiceConnection implements ServiceConnection {
    public static final String BROKEN_STRING = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n" + 
    "   <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" + "\n" + 
    "      <soapenv:Body>" + "\n" +
    "         <ComplexFunctionResponse soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" + "\n" + 
    "            <ComplexFunctionReturn href=\"#id0\"/>" + "\n" + 
    "         </ComplexFunctionResponse>" + "\n" + 
    "         <multiRef id=\"id0\" soapenc:root=\"0\" soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xsi:type=\"ns1:DeviceRegistrationResponse\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:ns1=\"http://namespace.com\">" + "\n" + 
    "            <longResponse href=\"#id1\"/>" + "\n" + 
    "            <stringResponse xsi:type=\"xsd:string\">theStringResponse</stringResponse>" + "\n" + 
    "         </multiRef>" + "\n" + 
    "         <multiRef id=\"id1\" soapenc:root=\"0\" soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xsi:type=\"xsd:long\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">1234567890</multiRef>" + "\n" + 
    "      </soapenv:Body>" + "\n" + 
    "   </soapenv:Envelope>";
    public static final String WORKING_STRING = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n" + 
    "   <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" + "\n" + 
    "      <soapenv:Body>" + "\n" + 
    "         <ComplexFunctionResponse soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" + "\n" + 
    "            <ComplexFunctionReturn href=\"#id0\"/>" + "\n" + 
    "         </ComplexFunctionResponse>" + "\n" + 
    "         <multiRef id=\"id0\" soapenc:root=\"0\" soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xsi:type=\"ns1:DeviceRegistrationResponse\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:ns1=\"http://namespace.com\">" + "\n" + 
    "            <longResponse xsi:type=\"xsd:long\">1234567890</longResponse>" + "\n" + 
    "            <stringResponse xsi:type=\"xsd:string\">theStringResponse</stringResponse>" + "\n" + 
    "         </multiRef>" + "\n" + 
    "         <multiRef id=\"id1\" soapenc:root=\"0\" soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xsi:type=\"xsd:long\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">10</multiRef>" + "\n" + 
    "      </soapenv:Body>" + "\n" + 
    "   </soapenv:Envelope>";
    public static final String WORKING_NOMULTIREF = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n" + 
    "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" + "\n" + 
    "    <soapenv:Body>" + "\n" + 
    "       <ComplexFunctionResponse soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" + "\n" + 
    "          <ComplexFunctionReturn xsi:type=\"ns1:ComplexFunctionResponse\" xmlns:ns1=\"http://namespace.com\">" + "\n" + 
    "             <longResponse xsi:type=\"xsd:long\">1234567890</longResponse>" + "\n" + 
    "             <stringResponse xsi:type=\"xsd:string\">theStringResponse</stringResponse>" + "\n" + 
    "          </ComplexFunctionReturn>" + "\n" + 
    "       </ComplexFunctionResponse>" + "\n" + 
    "    </soapenv:Body>" + "\n" + 
    "</soapenv:Envelope>";
    public static final String WORKING_NOMULTIREF_REVERSED_RESPONSE_PARAMETERS = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n" + 
    "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" + "\n" + 
    "    <soapenv:Body>" + "\n" + 
    "       <ComplexFunctionResponse soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" + "\n" + 
    "          <ComplexFunctionReturn xsi:type=\"ns1:ComplexFunctionResponse\" xmlns:ns1=\"http://namespace.com\">" + "\n" + 
    "             <stringResponse xsi:type=\"xsd:string\">theStringResponse</stringResponse>" + "\n" + 
    "             <longResponse xsi:type=\"xsd:long\">1234567890</longResponse>" + "\n" + 
    "          </ComplexFunctionReturn>" + "\n" + 
    "       </ComplexFunctionResponse>" + "\n" + 
    "    </soapenv:Body>" + "\n" + 
    "</soapenv:Envelope>";
    public static final Class RESPONSE_CLASS = new ComplexResponse().getClass();
    public static final String RESPONSE_NAME = "ComplexFunctionReturn";
    public static final String NAMESPACE = "http://namespace.com";
    
    private ByteArrayInputStream inputStream;
    public ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    
    public static InputStream createWorkingNoMultirefAsStream() {
        return new ByteArrayInputStream(WORKING_NOMULTIREF.getBytes());
    }
    public static InputStream createWorkingNoMultirefAsStream_reversedResponseParameters() {
        return new ByteArrayInputStream(WORKING_NOMULTIREF_REVERSED_RESPONSE_PARAMETERS.getBytes());
    }
        
    public void setInputSring(String inputString) {
        inputStream = new ByteArrayInputStream(inputString.getBytes());
    }
    
    public void connect() throws IOException {
        throw new RuntimeException("MockServiceConnection.connect is not implemented yet");
    }

    public void disconnect() throws IOException {
        throw new RuntimeException("MockServiceConnection.disconnect is not implemented yet");
    }

    public void setRequestProperty(String string, String soapAction) throws IOException {
    }

    public void setRequestMethod(String post) throws ProtocolException, IOException {
    }

    public OutputStream openOutputStream() throws IOException {
        return outputStream;
    }

    public InputStream openInputStream() throws IOException {
        return inputStream;
    }

    public InputStream getErrorStream() {
        throw new RuntimeException("MockServiceConnection.getErrorStream is not implemented yet");
    }

}
