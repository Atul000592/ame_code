package nic.ame.app.master.ref.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefEyeStatus {

        @Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
		private String e_Status;
		private String description;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getE_Status() {
			return e_Status;
		}
		public void setE_Status(String e_Status) {
			this.e_Status = e_Status;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		
		

}
