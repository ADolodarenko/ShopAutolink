package ru.flc.service.shopautolink.model.settings.type;

import ru.flc.service.shopautolink.model.Utils;

public class Password
{
	private String secret;
	
	public Password(String key)
	{
		if (key == null)
			secret = null;
		else
			secret = Utils.caesarCipherEncrypt(key,4);
	}
	
	public String getSecret()
	{
		return secret;
	}
	
	public char[] getKey()
	{
		if (secret == null)
			return null;
		else
			return Utils.caesarCipherDecrypt(secret, 4).toCharArray();
	}
}
