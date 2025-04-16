	function closeModel() {
			var closeButton = document.getElementById("myModal");
			closeButton.style.display = "none";
		}


		function userHistoryStatus(x) {
			console.log(x.attributes[0].value);
			var forcePersonalId = x.attributes[0].value;
			//alert(boardId);

			var modal1 = document.getElementById("myModal");

			// Get the button that opens the modal
			//var btn = document.getElementById("myBtn");

			// Get the <span> element that closes the modal
			var span = document.getElementsByClassName("modal1");

			// When the user clicks the button, open the modal 

			// When the user clicks on <span> (x), close the modal
			span.onclick = function () {
				modal1.style.display = "none";
			}

			// When the user clicks anywhere outside of the modal, close it
			window.onclick = function (event) {
				if (event.target == modal1) {
					modal1.style.display = "none";
				}
			}
			
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
		    var table = document.getElementById("myTable");
			while (table.rows.length > 0) {
						table.deleteRow(0);
					}
			$.ajax({

				type: "post",
                 url: "get-user-role-history",
				cache: false,
				dataType: 'json',

				data: {forcePersonalId: forcePersonalId},
				beforeSend: function (xhr) {

					xhr.setRequestHeader(header,token);
				},
				success: function (response) {


	
                  document.getElementById("irlaNo").innerHTML=response.forcePersonalDto.forceId ;
                  document.getElementById("name").innerHTML=response.forcePersonalDto.name ;
                  document.getElementById("rank").innerHTML=response.forcePersonalDto.rank ;
                    document.getElementById("forceName").innerHTML=response.forcePersonalDto.forceName ;
                      document.getElementById("unitName").innerHTML=response.forcePersonalDto.unitName ;
              

	
               createTable(response.roleListDto);
                  

				},
				error: function () {
					alert('Error while request..');
				}
			});
           
       modal1.style.display = "block";


		}
		
		//=====================create table========================//
		function createTable(response) {
		var trHTML = '';
		
		$.each(response, function (i, o) {
			trHTML +='<tr><td>' + (i+1) +
		     '</td><td>' + o.roleName +
				'</td><td>' + o.year +
				'</td><td>' + o.status +
				'</td></tr>';
		});

		$('#myTable').append(trHTML);

	}
	
	
	
	
		
		