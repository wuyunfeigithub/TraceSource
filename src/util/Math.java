package util;

public class Math {
	
	
	/**
	 * ��������������
	 * @param vector1
	 * @param vector2
	 */
	public static double multiplyVector(double[] vector1, double[] vector2)
	{
		double result = 0;
		if(vector1.length == vector2.length){
			for (int i = 0; i < vector1.length; i++) {
				result += vector1[i]*vector2[i];
			}
		}
		return result;
	}
	
	/**
	 * �ֶκ��������������㡢�����ֱ�ӳ���1��0��-1
	 * @param parameter
	 * @return
	 */
	public static int sgn(double parameter){
		if(parameter > 0){
			return 1;
		}
		if(parameter < 0){
			return -1;
		}
		return 0;
	}
	
	/**
	 * ���ز�����0�ϴ����
	 * @param parameter
	 * @return
	 */
	public static double max(double parameter){
		if(parameter > 0)
			return parameter;
		return 0;
			
	}
	
	/**
	 * ��������ʽ�������Ķ�����
	 * @param vector
	 * @return
	 */
	public static double norm2(double[] vector){
		double sum = 0;
		for (int i = 0; i < vector.length; i++) {
			sum += java.lang.Math.pow(vector[i], 2);
		}
		
		return java.lang.Math.sqrt(sum);
	}
	
	
	

}
