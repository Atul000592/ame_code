		
		function submitMapping () {
			
					var arr = new Array();
					var Myarray = new Array();
					//var board_id_a = $("#boardId").val();
					
					
                   var controllingOfficerIRLA = document.getElementById("controllingForcePersonalId").value;


					var token = $("meta[name='_csrf']").attr("content");
					var header = $("meta[name='_csrf_header']").attr("content");
					$.each($("input[name='candidateId']:checked"), function () {

						arr.push($(this).val());

					});
                    if(arr.length==0){
						alert("please select atleast one person for mapping...");
						window.location.reload();
						return false; 
						
									
									}
					arr.push(controllingOfficerIRLA);
				

					var data = JSON.stringify(arr);
                  $('#loading-overlay').show();
			  $('body').addClass('loading');

					$.ajax({

						type: "post",

						url: "map-individual-to-controlling-officer",
						cache: false,
						dataType: 'json',
						contentType: "application/json",
						data: data,
						beforeSend: function (xhr) {

							xhr.setRequestHeader(header, token);
						},
						success: function (response) {
						alert("record Save Successfuly");
						window.location.reload();

						},


						error: function () {
							alert('Error while request..');
							window.location.reload();
						},
						complete: function() {
                                  $('#loading-overlay').hide();
                                 $('body').removeClass('loading');
     }
					});


				
			}



