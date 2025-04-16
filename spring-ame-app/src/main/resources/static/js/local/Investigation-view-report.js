//====================================bloodSugar===========================================
	 
        $(document).ready(function() {
            var ameId = $('#ameId-bs').val();

            function getBloodSugarData() {
                $.ajax({
                    url: '/blood-sugar',
                    type: 'GET',
                    data: { ameId: ameId },
                    dataType: 'json',
                    success: function(response) {
                        $('#bspp').val(response.bloodSugarPP);
                        $('#bsf').val(response.bloodSugarF);
                        $('#bloodSugarRandom').val(response.bloodSugarRandom);
                        $('#HbA1c').val(response.hbA1c);
						$('#path').val(response.path);
                        $('#fileName').val(response.fileName);
						document.getElementById("pathValue").value=response.path;
                    },
                    error: function(error) {
                        console.log('Error fetching data:', error);
                    }
                });
            }

            // Call the function to load data when the document is ready
            getBloodSugarData();
		
        });

      //  ====================================Kft===========================================

      $(document).ready(function() {
        var ameId = $('#ameId-kft').val();

        function getKft() {
            $.ajax({
                url: '/view-investigation-report-kft',
                type: 'GET',
                data: { ameId: ameId },
                dataType: 'json',
                success: function(response) {
                    $('#bloodUrea').val(response.bloodUrea);
                    $('#scrumCreatinine').val(response.scrumCreatinine);
                    $('#uricAcid').val(response.uricAcid);
                    $('#path').val(response.path);
                    $('#fileNamekft').val(response.fileName);
                    document.getElementById("kftpath").value=response.path;
                },
                error: function(error) {
                    console.log('Error fetching data:', error);
                }
            });
        }

        // Call the function to load data when the document is ready
        getKft();
    
    });

 
          //  ====================================lipid===========================================

         
          $(document).ready(function() {
            var ameId = $('#ameId-lipid').val();
            function getlipid() {
              
                $.ajax({
                    url: 'view-investigation-report-lipid',
                    type: 'GET',
                    data: { ameId: ameId },
                    dataType: 'json',
                    success: function(response) {
                        $('#serumCholesterol').val(response.serumCcholesterol);
                        $('#lDLCholesterol').val(response.lDLCholesterol);
                        $('#hDLCholesterol').val(response.hDLCholesterol);
                        $('#vLDLcholesterol').val(response.vLDLcholesterol);
                        $('#triglycerides').val(response.triglycerides);
                        $('#lDLhDLRatio').val(response.lDLhDLRatio);
                        $('#lipidpath').val(response.path);
                        $('#fileNamelipid').val(response.fileName);
                        document.getElementById("lipidpath").value=response.path;
                    },
                    error: function(error) {
                        console.log('Error fetching data:', error);
                    }
                });
            }
            getlipid();
    
        });
    
           
          //  ====================================cbc===========================================

         
          $(document).ready(function() {
            var ameId = $('#ameId-cbc').val();
            function getcbc() {
              
                $.ajax({
                    url: 'view-investigation-report-cbc',
                    type: 'GET',
                    data: { ameId: ameId },
                    dataType: 'json',
                    success: function(response) {
                        $('#haemoglobin').val(response.haemoglobin);
                        $('#totalLeukocyteCount').val(response.totalLeukocyteCount);
                        $('#neutrophilsOrPolymorphs').val(response.neutrophilsOrPolymorphs);
                        $('#monocytes').val(response.monocytes);
                        $('#lymphocyte').val(response.lymphocytesBTCells);
                        $('#basophils').val(response.basophils);
                        $('#eosinophils').val(response.eosinophils);
                        $('#erythrocyteSedimentationRate').val(response.erythrocyteSedimentationRate);
                        $('#cbcpath').val(response.path);
                        $('#fileNamecbc').val(response.fileName);
                        document.getElementById("cbcpath").value=response.path;
                    },
                    error: function(error) {
                        console.log('Error fetching data:', error);
                    }
                });
            }
            getcbc();
    
        });


        //  ====================================viralmarkers===========================================

         
        $(document).ready(function() {
            var ameId = $('#ameId-vrl').val();
            function getmarkers() {
              
                $.ajax({
                    url: 'view-investigation-report-viral-markers',
                    type: 'GET',
                    data: { ameId: ameId },
                    dataType: 'json',
                    success: function(response) {
                        $('#hiv1').val(response.hiv1);
                        $('#hiv2').val(response.hiv2);
                        $('#hbsAg').val(response.hbsAg);
                        $('#vdrl').val(response.vdrl);
                        $('#hcv').val(response.hcv);
                        $('#vrlpath').val(response.path);
                        $('#fileNamevrl').val(response.fileName);
                        document.getElementById("vrlpath").value=response.path;
                    },
                    error: function(error) {
                        console.log('Error fetching data:', error);
                    }
                });
            }
            getmarkers();
    
        });

        //=========================================ecg=====================================

        $(document).ready(function() {
            var ameId = $('#ecg-ameId').val();
            function getOthers() {
              
                $.ajax({
                    url: 'view-investigation-report-ecg',
                    type: 'GET',
                    data: { ameId: ameId },
                    dataType: 'json',
                    success: function(response) {
                        $('#ecg-testName').val(response.testName);
                       // $('#testCode').val(response.testCode);
                        $('#ecgpath').val(response.path);
                        $('#fileNameecg').val(response.fileName);
                        document.getElementById("vrlpath").value=response.path;
                    },
                    error: function(error) {
                        console.log('Error fetching data:', error);
                    }
                });
            }
            getOthers();
    
        });



                //=========================================usg=====================================

                $(document).ready(function() {
                    var ameId = $('#usg-ameId').val();
                    function getusg() {
                      
                        $.ajax({
                            url: 'view-investigation-report-usg',
                            type: 'GET',
                            data: { ameId: ameId },
                            dataType: 'json',
                            success: function(response) {
                                $('#usg-testName').val(response.testName);
                               // $('#testCode').val(response.testCode);
                                $('#usgpath').val(response.path);
                                $('#fileNameusg').val(response.fileName);
                                document.getElementById("usgpath").value=response.path;
                            },
                            error: function(error) {
                                console.log('Error fetching data:', error);
                            }
                        });
                    }
                    getusg();
            
                });
    //===========================================xray==================================================

                $(document).ready(function() {
                    var ameId = $('#xray-ameId').val();
                    function getxray() {
                      
                        $.ajax({
                            url: 'view-investigation-report-xray',
                            type: 'GET',
                            data: { ameId: ameId },
                            dataType: 'json',
                            success: function(response) {
                                $('#xray-testName').val(response.testName);
                               // $('#testCode').val(response.testCode);
                                $('#xraypath').val(response.path);
                                $('#fileNamexray').val(response.fileName);
                                document.getElementById("xraypath").value=response.path;
                            },
                            error: function(error) {
                                console.log('Error fetching data:', error);
                            }
                        });
                    }
                    getxray();
            
                });
    
           //===========================================Urine Test==================================================

           $(document).ready(function() {
            var ameId = $('#ameId-utmicro').val();
            function geturine() {
              
                $.ajax({
                    url: 'view-investigation-report-urine-test',
                    type: 'GET',
                    data: { ameId: ameId },
                    dataType: 'json',
                    success: function(response) {
                        $('#epithelialCells').val(response.epithelialCells);
                        $('#prostaticIntraepithelialNeoplasia').val(response.prostaticIntraepithelialNeoplasia);
                        $('#rbc').val(response.rbc);
                        $('#crystal').val(response.crystal);
                        $('#colour').val(response.colour);
                        $('#transparency').val(response.transparency);
                        $('#reaction').val(response.reaction);
                        $('#albuminCells').val(response.albuminCells);
                        $('#sugar').val(response.sugar);
                        $('#ketones').val(response.ketones);
                        $('#specificGravity').val(response.specificGravity);
                        $('#bileSalt').val(response.bileSalt);
                        $('#bilePigment').val(response.bilePigment);
                        $('#appearance').val(response.appearance);
                        $('#urinePregnancyTest').val(response.urinePregnancyTest);
                        $('#ph').val(response.ph);
                        $('#castCells').val(response.castCells);
                        $('#phosphate').val(response.pshosphate);
                        $('#bilirubin').val(response.bilirubin);
                        $('#urobilinogen').val(response.urobilinogen);
                        $('#bacteria').val(response.bacteria);
                        $('#eosinophils').val(response.eosinophils);
                        $('#xray-testName').val(response.testName);
                       // $('#testCode').val(response.testCode);
                        $('#urinepath').val(response.path);
                        $('#fileNameurine').val(response.fileName);
                        document.getElementById("urinepath").value=response.path;
                    },
                    error: function(error) {
                        console.log('Error fetching data:', error);
                    }
                });
            }
            geturine();
    
        });

//===================Thyroid Profile==========================================================

        $(document).ready(function() {
            var ameId = $('#ameId-tp').val();
            function getthyroid() {
              
                $.ajax({
                    url: 'view-investigation-report-thyroid-profile',
                    type: 'GET',
                    data: { ameId: ameId },
                    dataType: 'json',
                    success: function(response) {
                        $('#t3').val(response.t3);
                        $('#t4').val(response.t4);
                        $('#tsh').val(response.tsh);
                        $('#tppath').val(response.path);
                        $('#fileNametp').val(response.fileName);
                        document.getElementById("tppath").value=response.path;
                    },
                    error: function(error) {
                        console.log('Error fetching data:', error);
                    }
                });
            }
            getthyroid();
    
        });


          //  ====================================Lft===========================================

         
          $(document).ready(function() {
            var ameId = $('#ameId-lft').val();
            function getlft() {
              
                $.ajax({
                    url: 'view-investigation-report-lft',
                    type: 'GET',
                    data: { ameId: ameId },
                    dataType: 'json',
                    success: function(response) {
                        $('#bilirubin').val(response.bilirubin);
                        $('#directBilirubin').val(response.directBilirubin);
                        $('#indirectBilirubin').val(response.indirectBilirubin);
                        $('#sGOT').val(response.sGOT);
                        $('#sGPT').val(response.sGPT);
                        $('#aLP').val(response.aLP);
                        $('#lftpath').val(response.path);
                        $('#fileNamelft').val(response.fileName);
                        document.getElementById("lftpath").value=response.path;
                    },
                    error: function(error) {
                        console.log('Error fetching data:', error);
                    }
                });
            }
            getlft();
    
        });

   



        
    
    