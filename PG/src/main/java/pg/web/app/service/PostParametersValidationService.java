package pg.web.app.service;

import pg.web.app.exception.InvalidPostParametersException;
import pg.web.app.model.Post;

public class PostParametersValidationService {
	
	public static final int SHORT_MAX_LENGTH = 100;
	public static final int LONG_MAX_LENGTH = 10000;
	
	public static void validatePostParameters(Post post) throws InvalidPostParametersException {
		validateParameter(post.getTitle(), SHORT_MAX_LENGTH);
		validateParameter(post.getText(), LONG_MAX_LENGTH);
	}

	public static void validateParameter(String parameter, int maxLength) throws InvalidPostParametersException {
		if(parameter.length() > maxLength) {
			throw new InvalidPostParametersException("Invalid parameter length");
		}
		if(parameter.isEmpty()) {
			throw new InvalidPostParametersException("Empty parameter entered");
		}
	}
}
