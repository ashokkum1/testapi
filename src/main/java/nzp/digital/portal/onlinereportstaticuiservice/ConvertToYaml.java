package nzp.digital.portal.onlinereportstaticuiservice;

import java.io.File;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonSchema.factories.SchemaFactoryWrapper;
import com.fasterxml.jackson.databind.jsonSchema.types.JsonSchema;

import nzp.digital.portal.onlinereportstaticuiservice.model.Address;
import nzp.digital.portal.onlinereportstaticuiservice.payload.OnlineReportCaseRequest;

public class ConvertToYaml {

	public static void main(String[] args) {
		try {
		// Create an ObjectMapper mapper for YAML
		//ObjectMapper mapper = new ObjectMapper(new JsonFactory());

		// Write object as YAML file
		//mapper.writeValue(new File("C:\\test\\output.yaml"), new OnlineReportCaseRequest());
			
			ObjectMapper m = new ObjectMapper();
			SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
			m.acceptJsonFormatVisitor(Address.class, visitor);
			JsonSchema jsonSchema = visitor.finalSchema();

			ObjectMapper mapper = new ObjectMapper(new JsonFactory());
			m.writeValue(new File("C:\\test\\output.yaml"), jsonSchema);
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

}
