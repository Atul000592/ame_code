
/*===============investigation CBC ================================*/

function fetchColumnValuescbc() {
    event.preventDefault();
    var columnValues = [];
    var hashMap = new Map();
    var fileSectionName;
    var testCodeId=document.getElementById("testCodeId").value;
    $('#myTable tr.memberDatacbc').each(function () {
        var rowData = {};
        $(this).find('td').each(function (index) {
            if (index === 0) {
                rowData.testName = $(this).find('label').text();
            } else if (index === 1) {
                rowData.subTestCode = $(this).find('input').val();
                rowData.subTestCodeName = $(this).find('input').attr('name');
                if(rowData.subTestCodeName=="file"){
					fileSectionName=$(this).find('input').attr('id');
				}
            }
        });
        hashMap.set(rowData.subTestCodeName, rowData.subTestCode);
        columnValues.push(rowData);
    });
    
    var fileinfo=$('#'+fileSectionName)[0].files[0];
    
    if(fileinfo==null){
		alert("Please select file To upload.......!");
		return false;
			}
    var formData = new FormData();
    
    formData.append('file',fileinfo);
    
    var ameId = document.getElementById("ameId").value;
    
   formData.append("ameId",ameId);
   formData.append("testCodeId",testCodeId);

    var jsonArray = [];
    hashMap.forEach(function (value, key) {
        var obj = {};
        obj[key] = value;
        jsonArray.push(obj);
    });

    // Convert the array to a JSON string
    var jsonData = JSON.stringify(jsonArray);
    formData.append("jsonData", jsonData);

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        type: 'POST',
        url: 'upload-investigation-final-report-form-final',
        data: formData,
        contentType: false,
        processData: false,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            //console.log(data);
            alert("Report uploaded successfully..........!")
           
            location.reload(true);
        },
        error: function (e) {
            console.log("ERROR:", e);
        }
    });
}


/*=========================Urine MicroScope And Urine Physical================================*/
function fetchColumnValuesutmicro() {
    event.preventDefault();
    var columnValues = [];
    var hashMap = new Map();
  
    var testCodeId=document.getElementById("testCodeId").value;
    $('#myTable tr.memberDatautmicrophy').each(function () {
        var rowData = {};
        $(this).find('td').each(function (index) {
            if (index === 0) {
                rowData.testName = $(this).find('label').text();
            } else if (index === 1) {
                rowData.subTestCode = $(this).find('input').val();
                rowData.subTestCodeName = $(this).find('input').attr('name');
                if(rowData.subTestCodeName=="file"){
					fileSectionName=$(this).find('input').attr('id');
				}
            }
        });
        hashMap.set(rowData.subTestCodeName, rowData.subTestCode);
        columnValues.push(rowData);
    });
    
      var fileSectionName="fileInput"+testCodeId;
    var fileinfo=$('#'+fileSectionName)[0].files[0];
    
    if(fileinfo==null){
		alert("Please select file To upload.......!");
		return false;
			}
    var formData = new FormData();
    
    formData.append('file',fileinfo);
    
    var ameId = document.getElementById("ameId").value;
    
   formData.append("ameId",ameId);
   formData.append("testCodeId",testCodeId);

    var jsonArray = [];
    hashMap.forEach(function (value, key) {
        var obj = {};
        obj[key] = value;
        jsonArray.push(obj);
    });

    // Convert the array to a JSON string
    var jsonData = JSON.stringify(jsonArray);
    formData.append("jsonData", jsonData);
  console.log(hashMap);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        type: 'POST',
        url: 'upload-investigation-final-report-form-final',
        data: formData,
        contentType: false,
        processData: false,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            //console.log(data);
            alert("Report uploaded successfully..........!")
           
            location.reload(true);
        },
        error: function (e) {
            console.log("ERROR:", e);
        }
    });
}




/*===============================Lipid=====================================*/
function fetchColumnValueslipid() {
    event.preventDefault();
    var columnValues = [];
    var hashMap = new Map();
  
    var testCodeId=document.getElementById("testCodeId").value;
    $('#myTable tr.memberDatalipid').each(function () {
        var rowData = {};
        $(this).find('td').each(function (index) {
            if (index === 0) {
                rowData.testName = $(this).find('label').text();
            } else if (index === 1) {
                rowData.subTestCode = $(this).find('input').val();
                rowData.subTestCodeName = $(this).find('input').attr('name');
                if(rowData.subTestCodeName=="file"){
					fileSectionName=$(this).find('input').attr('id');
				}
            }
        });
        hashMap.set(rowData.subTestCodeName, rowData.subTestCode);
        columnValues.push(rowData);
    });
    
      var fileSectionName="fileInput"+testCodeId;
    var fileinfo=$('#'+fileSectionName)[0].files[0];
    
    if(fileinfo==null){
		alert("Please select file To upload.......!");
		return false;
			}
    var formData = new FormData();
    
    formData.append('file',fileinfo);
    
    var ameId = document.getElementById("ameId").value;
    
   formData.append("ameId",ameId);
   formData.append("testCodeId",testCodeId);

    var jsonArray = [];
    hashMap.forEach(function (value, key) {
        var obj = {};
        obj[key] = value;
        jsonArray.push(obj);
    });

    // Convert the array to a JSON string
    var jsonData = JSON.stringify(jsonArray);
    formData.append("jsonData", jsonData);
  console.log(hashMap);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        type: 'POST',
        url: 'upload-investigation-final-report-form-final',
        data: formData,
        contentType: false,
        processData: false,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            //console.log(data);
            alert("Report uploaded successfully..........!")
           
            location.reload(true);
        },
        error: function (e) {
            console.log("ERROR:", e);
        }
    });
}




/*========================Blood Sugar===============================*/

function fetchColumnValuesbs() {
    event.preventDefault();
    var columnValues = [];
    var hashMap = new Map();
    
    var testCodeId=document.getElementById("testCodeId").value;
    $('#myTable tr.memberDatabs').each(function () {
        var rowData = {};
        $(this).find('td').each(function (index) {
            if (index === 0) {
                rowData.testName = $(this).find('label').text();
            } else if (index === 1) {
                rowData.subTestCode = $(this).find('input').val();
                rowData.subTestCodeName = $(this).find('input').attr('name');
                if(rowData.subTestCodeName=="file"){
					fileSectionName=$(this).find('input').attr('id');
				}
            }
        });
        hashMap.set(rowData.subTestCodeName, rowData.subTestCode);
        columnValues.push(rowData);
    });
    var fileSectionName="fileInput"+testCodeId;
    var fileinfo=$('#'+fileSectionName)[0].files[0];
    
    if(fileinfo==null){
		alert("Please select file To upload.......!");
		return false;
			}
    var formData = new FormData();
    
    formData.append('file', fileinfo);
    
    var ameId = document.getElementById("ameId").value;
    
   formData.append("ameId",ameId);
   formData.append("testCodeId",testCodeId);

    var jsonArray = [];
    hashMap.forEach(function (value, key) {
        var obj = {};
        obj[key] = value;
        jsonArray.push(obj);
    });

    // Convert the array to a JSON string
    var jsonData = JSON.stringify(jsonArray);
    formData.append("jsonData", jsonData);
  console.log(hashMap);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        type: 'POST',
        url: 'upload-investigation-final-report-form-final',
        data: formData,
        contentType: false,
        processData: false,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            //console.log(data);
            alert("Report uploaded successfully..........!")
           
            location.reload(true);
        },
        error: function (e) {
            console.log("ERROR:", e);
        }
    });
}

/*====================KFT(Kidney Fuction Test)===========================*/

function fetchColumnValueskft() {
    event.preventDefault();
    var columnValues = [];
    var hashMap = new Map();
    
    var testCodeId=document.getElementById("testCodeId").value;
    $('#myTable tr.memberDataKft').each(function () {
        var rowData = {};
        $(this).find('td').each(function (index) {
            if (index === 0) {
                rowData.testName = $(this).find('label').text();
            } else if (index === 1) {
                rowData.subTestCode = $(this).find('input').val();
                rowData.subTestCodeName = $(this).find('input').attr('name');
                if(rowData.subTestCodeName=="file"){
					fileSectionName=$(this).find('input').attr('id');
				}
            }
        });
        hashMap.set(rowData.subTestCodeName, rowData.subTestCode);
        columnValues.push(rowData);
    });
    var fileSectionName="fileInput"+testCodeId;
    var fileinfo=$('#'+fileSectionName)[0].files[0];
    
    if(fileinfo==null){
		alert("Please select file To upload.......!");
		return false;
			}
    var formData = new FormData();
    
    formData.append('file', fileinfo);
    
    var ameId = document.getElementById("ameId").value;
    
   formData.append("ameId",ameId);
   formData.append("testCodeId",testCodeId);

    var jsonArray = [];
    hashMap.forEach(function (value, key) {
        var obj = {};
        obj[key] = value;
        jsonArray.push(obj);
    });

    // Convert the array to a JSON string
    var jsonData = JSON.stringify(jsonArray);
    formData.append("jsonData", jsonData);
  console.log(hashMap);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        type: 'POST',
        url: 'upload-investigation-final-report-form-final',
        data: formData,
        contentType: false,
        processData: false,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            //console.log(data);
            alert("Report uploaded successfully..........!")
           
            location.reload(true);
        },
        error: function (e) {
            console.log("ERROR:", e);
        }
    });
}
/*=================LFT(Liver Fuction Test)===============================*/

function fetchColumnValueslft() {
    event.preventDefault();
    var columnValues = [];
    var hashMap = new Map();
    
    var testCodeId=document.getElementById("testCodeId").value;
    $('#myTable tr.memberDataLft').each(function () {
        var rowData = {};
        $(this).find('td').each(function (index) {
            if (index === 0) {
                rowData.testName = $(this).find('label').text();
            } else if (index === 1) {
                rowData.subTestCode = $(this).find('input').val();
                rowData.subTestCodeName = $(this).find('input').attr('name');
                if(rowData.subTestCodeName=="file"){
					fileSectionName=$(this).find('input').attr('id');
				}
            }
        });
        hashMap.set(rowData.subTestCodeName, rowData.subTestCode);
        columnValues.push(rowData);
    });
    var fileSectionName="fileInput"+testCodeId;
    var fileinfo=$('#'+fileSectionName)[0].files[0];
    
    if(fileinfo==null){
		alert("Please select file To upload.......!");
		return false;
			}
    var formData = new FormData();
    
    formData.append('file', fileinfo);
    
    var ameId = document.getElementById("ameId").value;
    
   formData.append("ameId",ameId);
   formData.append("testCodeId",testCodeId);

    var jsonArray = [];
    hashMap.forEach(function (value, key) {
        var obj = {};
        obj[key] = value;
        jsonArray.push(obj);
    });

    // Convert the array to a JSON string
    var jsonData = JSON.stringify(jsonArray);
    formData.append("jsonData", jsonData);
  console.log(hashMap);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        type: 'POST',
        url: 'upload-investigation-final-report-form-final',
        data: formData,
        contentType: false,
        processData: false,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            //console.log(data);
            alert("Report uploaded successfully..........!")
           
            location.reload(true);
        },
        error: function (e) {
            console.log("ERROR:", e);
        }
    });
}































function backToHome() {

			$("#back-to-home").submit();
		}

/*=======================upload report =================================*/

function uploadReport(e){
	const testCode = e.value;
	const operation = e.name;
	
	const ameId = document.getElementById("ameId").value;

    const modelIdName="investigation"+testCode;

	const modelId = document.getElementById(modelIdName);
 
    const spanName="investigation"+ testCode+"Class";
    
	const span = document.getElementsByClassName(spanName);
	
	const inputElementCall="."+spanName+" "+"input";
	
	
	const inputs = document.querySelectorAll(inputElementCall);
	
	
	
	
	if(operation === "view"){
	/*	const parentCallString="."+spanName+" "+"#file-div div";*/
		const label="."+spanName+" "+"#file-div label";
		const anchorTag="."+spanName+" "+"#file-div a";
	/*	const parent = document.querySelector(parentCallString);*/
		const childToRemove="#fileInput-"+testCode;
	/*	parent.removeChild(childToRemove);*/
	
	const submitButton=$("#save-"+testCode);
	submitButton.hide();
		$(childToRemove).hide();
		$(label).text("Download Report");
		$(anchorTag).show();
		inputs.forEach(input=>{
			
			input.readOnly=true;
			
		});
		
		
		
	}
	else if(operation=== "edit"){
		/*	const parentCallString="."+spanName+" "+"#file-div div";*/
		const label="."+spanName+" "+"#file-div label";
		const anchorTag="."+spanName+" "+"#file-div a";
	/*	const parent = document.querySelector(parentCallString);*/
		const childToRemove="#fileInput-"+testCode;
	/*	parent.removeChild(childToRemove);*/
		$(childToRemove).show();
		$(label).text("Upload Report");
		$(anchorTag).show();
		inputs.forEach(input=>{
			
			input.readOnly=false;
	});
	}
	
	
    /*document.getElementById("testCodeId").value=testCode;
    */ 
	// When the user clicks the button, open the modal 
	modelId.style.display = "block";
	$("#"+modelIdName).show();
	// When the user clicks on <span> (x), close the modal
	span.onclick = function() {
		modelId.style.display = "none";
	}

	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
		if (event.target == modelId) {
			modelId.style.display = "none";
		}
	}

}	
	


/*=========================model close button===================================*/



function investigationcbcCloseModel() {
	var closeButton = document.getElementById("investigationcbc");

	closeButton.style.display = "none";
}



function investigationutmicrophyCloseModel() {
	var closeButton = document.getElementById("investigationutmicro");

	closeButton.style.display = "none";
}

function investigationlipidCloseModel() {
	var closeButton = document.getElementById("investigationlipid");

	closeButton.style.display = "none";
}


function investigationbsCloseModel() {
	var closeButton = document.getElementById("investigationbs");

	closeButton.style.display = "none";
}

function investigationlftCloseModel() {
	var closeButton = document.getElementById("investigationlft");

	closeButton.style.display = "none";
}
function investigationkftCloseModel() {
	var closeButton = document.getElementById("investigationkft");

	closeButton.style.display = "none";
}

function investigationOtherTestCloseModel() {
	var closeButton = document.getElementById("investigationOtherTest");

	closeButton.style.display = "none";
}




/*================calulate test=========================*/

function calculateRatio(){
	
	var lDLCholesterol=document.getElementById("lDLCholesterol").value;
	
	if(lDLCholesterol==""){
		alert("Please fill the value for LDL Cholesterol");
		return false;
	}
	
	var hDLCholesterol=document.getElementById("hDLCholesterol").value;
	
	var ratio=(lDLCholesterol/hDLCholesterol);
	var ratioFixed = ratio.toFixed(2);
	
	document.getElementById("lDLhDLRatio").value=ratioFixed;
	
	
	
}
function investigationvrlCloseModel() {
	var closeButton = document.getElementById("investigationvrl");

	closeButton.style.display = "none";
}

function investigationtpCloseModel() {
	var closeButton = document.getElementById("investigationtp");

	closeButton.style.display = "none";
}
function investigationotherCloseModel() {
	var closeButton = document.getElementById("investigationecg");

	closeButton.style.display = "none";
}

function investigationusgCloseModel() {
	var closeButton = document.getElementById("investigationusg");

	closeButton.style.display = "none";
}
function investigationxrayCloseModel() {
	var closeButton = document.getElementById("investigationxray");

	closeButton.style.display = "none";
}
function investigationecgCloseModel() {
	var closeButton = document.getElementById("investigationecg");

	closeButton.style.display = "none";
}
function investigationecgCloseModel() {
	var closeButton = document.getElementById("investigationecg");

	closeButton.style.display = "none";
}

function investigationbsppCloseModel() {
	var closeButton = document.getElementById("investigationbspp");

	closeButton.style.display = "none";
}
function investigationbsfCloseModel() {
	var closeButton = document.getElementById("investigationbsf");

	closeButton.style.display = "none";
}
function investigationbsrandomCloseModel() {
	var closeButton = document.getElementById("investigationbsrandom");

	closeButton.style.display = "none";
}
function investigationbsHbA1cCloseModel() {
	var closeButton = document.getElementById("investigationbsHbA1c");

	closeButton.style.display = "none";
}




