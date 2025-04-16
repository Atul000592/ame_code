package nic.ame.app.admin.model;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import nic.ame.app.esign.EsignRequestData;


@Entity
@Table(name="signature_document_type")
public class SignatureDocumentType {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String signatureType;
    @OneToOne
    @JoinColumn(name = "esign_request_id") 
    private EsignRequestData esignRequest;



	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
    public String getSignatureType() {
        return signatureType;
    }
    public void setSignatureType(String signatureType) {
        this.signatureType = signatureType;
    }
	
	
}


