SECTION default {
	@startIntent.setComponent("com.example.linkusv1.MainActivity")
	startActivity(@startIntent)
}

SECTION com.example.linkusv1.MainActivity{
	$login_button.onClick()
	

}
