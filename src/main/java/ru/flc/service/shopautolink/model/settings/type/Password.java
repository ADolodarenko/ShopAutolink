package ru.flc.service.shopautolink.model.settings.type;

import ru.flc.service.shopautolink.model.Utils;

public class Password
{
	private static final int OFFSET = 4;
	private String secret;
	
	public Password(char[] key)
	{
		if (key == null)
			secret = null;
		else
			secret = Utils.caesarCipherEncrypt(new String(key),OFFSET);
	}

	public Password(String secret)
	{
		this.secret = secret;
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
			return Utils.caesarCipherDecrypt(secret, OFFSET).toCharArray();
	}
}
