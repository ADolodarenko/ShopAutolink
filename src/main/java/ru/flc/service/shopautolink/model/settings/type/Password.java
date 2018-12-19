package ru.flc.service.shopautolink.model.settings.type;

import ru.flc.service.shopautolink.model.DataUtils;

import java.util.Objects;

public class Password
{
	private static final int OFFSET = 4;
	private String secret;
	
	public Password(char[] key)
	{
		if (key == null)
			secret = null;
		else
			secret = DataUtils.caesarCipherEncrypt(new String(key),OFFSET);
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
			return DataUtils.caesarCipherDecrypt(secret, OFFSET).toCharArray();
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(this.secret);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) return false;

		Class<?> objClass = obj.getClass();
		if (!objClass.equals(this.getClass())) return false;

		Password that = (Password) obj;

		return Objects.equals(that.secret, this.secret);
	}
}
