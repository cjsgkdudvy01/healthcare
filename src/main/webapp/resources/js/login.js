window.onload = function(){
	document.getElementById('login_btn').onclick = function(){
		loginId = document.getElementById('loginInputId');
		loginPwd = document.getElementById('loginInputPassword');
		if(check_input_date("아이디", loginId)){
			return "IDX";
		}
		if(check_input_date("비밀번호", loginPwd)){
			return "PWX";
		}
		var loginForm = document.getElementById('loginForm');
		loginForm.submit();
		
	}
	function check_input_date(alertStr, data){
		if(data.value == ""){
			alert(alertStr+"를 확인해 주세요");
			data.focus();
			return true;
		}
			return false;
	}
}