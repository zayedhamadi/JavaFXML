package Classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Client {
	
	private StringProperty codeCli;
	private StringProperty nomCli;
	private StringProperty adresseCli;
	private StringProperty emailCli;

	public Client(String codeCli, String nomCli, String adresseCli, String emailCli) {
		this.codeCli = new SimpleStringProperty(codeCli);
		this.nomCli = new SimpleStringProperty(nomCli);
		this.adresseCli = new SimpleStringProperty(adresseCli);
		this.emailCli = new SimpleStringProperty(emailCli);
	}

	public String getCodeCli() {
		return this.codeCli.get();
	}

	public void setCodeCli(String c) {
		this.codeCli.set(c);
	}

	public String getNomCli() {
		return this.nomCli.get();
	}

	public String getAdresseCli() {
		return adresseCli.get();
	}

	public String getEmailCli() {
		return emailCli.get();
				
	}

	public void setNomCli(String nomCli) {
	    this.nomCli.set(nomCli);
	}

	public void setAdresseCli(String adresseCli) {
	    this.adresseCli.set(adresseCli);
	}

	public void setEmailCli(String emailCli) {
	    this.emailCli.set(emailCli);
	}

	


}
