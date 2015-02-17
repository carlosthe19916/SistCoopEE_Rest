package org.softgreen.sistcoop.organizacion.restapi.exceptions;

import java.util.Iterator;
import java.util.List;

import org.jboss.resteasy.api.validation.Validation;

import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintDefinitionException;
import javax.validation.GroupDefinitionException;
import javax.validation.ValidationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

	@Override
	public Response toResponse(ValidationException exception) {
		if (exception instanceof ConstraintDefinitionException) {
			return buildResponse(unwrapException(exception), MediaType.TEXT_PLAIN, Status.INTERNAL_SERVER_ERROR);
		}
		if (exception instanceof ConstraintDeclarationException) {
			return buildResponse(unwrapException(exception), MediaType.TEXT_PLAIN, Status.INTERNAL_SERVER_ERROR);
		}
		if (exception instanceof GroupDefinitionException) {
			return buildResponse(unwrapException(exception), MediaType.TEXT_PLAIN, Status.INTERNAL_SERVER_ERROR);
		}
		return buildResponse(unwrapException(exception), MediaType.TEXT_PLAIN, Status.INTERNAL_SERVER_ERROR);
	}

	protected Response buildResponse(Object entity, String mediaType, Status status) {
		ResponseBuilder builder = Response.status(status).entity(entity);
		builder.type(MediaType.TEXT_PLAIN);
		builder.header(Validation.VALIDATION_HEADER, "true");
		return builder.build();
	}

	protected String unwrapException(Throwable t) {
		StringBuffer sb = new StringBuffer();
		doUnwrapException(sb, t);
		return sb.toString();
	}

	private void doUnwrapException(StringBuffer sb, Throwable t) {
		if (t == null) {
			return;
		}
		sb.append(t.toString());
		if (t.getCause() != null && t != t.getCause()) {
			sb.append('[');
			doUnwrapException(sb, t.getCause());
			sb.append(']');
		}
	}

	private MediaType getAcceptMediaType(List<MediaType> accept) {
		Iterator<MediaType> it = accept.iterator();
		while (it.hasNext()) {
			MediaType mt = it.next();
			if (MediaType.APPLICATION_JSON_TYPE.getType().equals(mt.getType()) && MediaType.APPLICATION_JSON_TYPE.getSubtype().equals(mt.getSubtype())) {
				return MediaType.APPLICATION_JSON_TYPE;
			}
		}
		return null;
	}

}