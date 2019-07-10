package hr.fer.zemris.java.hw11.jnotepadpp.models;

public interface SingleDocumentListener {

	void documentModifyStatusUpdated(SingleDocumentModel model);

	void documentFilePathUpdated(SingleDocumentModel model);

}
