let arrow = document.querySelectorAll(".arrow");
for (var i = 0; i < arrow.length; i++) {
  arrow[i].addEventListener("click", (e)=>{
 let arrowParent = e.target.parentElement.parentElement;//selecting main parent of arrow
 arrowParent.classList.toggle("showMenu");
  });
}

let sidebar = document.querySelector(".sidebar");
let sidebarBtn = document.querySelector(".bx-menu");
console.log(sidebarBtn);
sidebarBtn.addEventListener("click", ()=>{
  sidebar.classList.toggle("close");
});



		$("#Total-AME-view").click(function () {
			$("#Total-AME").show();
			$("#Today-AME").hide();
			$("#Total-AME-Force").hide();
			$("#Today-AME-Force").hide();

		});
		$("#Today-AME-view").click(function () {
			$("#Total-AME").hide();
			$("#Today-AME").show();
			$("#Total-AME-Force").hide();
			$("#Today-AME-Force").hide();
		});
		$("#Total-AME-Force-view").click(function () {
			$("#Total-AME").hide();
			$("#Today-AME").hide();
			$("#Total-AME-Force").show();
			$("#Today-AME-Force").hide();
		});
		$("#Today-AME-Force-view").click(function () {
			$("#Total-AME").hide();
			$("#Today-AME").hide();
			$("#Total-AME-Force").hide();
			$("#Today-AME-Force").show();
		});


