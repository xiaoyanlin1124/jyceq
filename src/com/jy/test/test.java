package com.jy.test;

import java.lang.reflect.Field;

import com.jy.entity.system.user.UserCamear;

public class test {
	
	public static String getString(Object o, Class< ? > c )
	{
		String result = c.getSimpleName( ) + ":";

		// 获取父类。推断是否为实体类
		if ( c.getSuperclass( ).getName( ).indexOf( "entity" ) >= 0 )
		{
			result +="\n<" +getString( o , c.getSuperclass( ) )+">,\n";
		}

		// 获取类中的全部定义字段
		Field[ ] fields = c.getDeclaredFields( );

		// 循环遍历字段，获取字段相应的属性值
		for ( Field field : fields )
		{
			// 假设不为空。设置可见性，然后返回
			field.setAccessible( true );

			try
			{
				// 设置字段可见，就可以用get方法获取属性值。

				result += field.getName( ) + "=" + field.get( o ) +",\n";
			}
			catch ( Exception e )
			{
				// System.out.println("error--------"+methodName+".Reason is:"+e.getMessage());
			}
		}
		if(result.indexOf( "," )>=0) result = result.substring( 0 , result.length( )-2 );
		return result;
	}

	 public static void main(String[] args) {
		 
		 UserCamear uu = new UserCamear();
		 uu.setAccess_token("123456");
		 uu.setEmail("87654");
		 
		 System.out.println(getString(uu ,uu.getClass() ));
		 
		 reflect(uu);
	 }
	 public static void reflect(Object o){
		  //获取参数类
		  Class cls = o.getClass();
		  //将参数类转换为对应属性数量的Field类型数组（即该类有多少个属性字段 N 转换后的数组长度即为 N）
		  Field[] fields = cls.getDeclaredFields();
		  for(int i = 0;i < fields.length; i ++){
		   Field f = fields[i];
		   f.setAccessible(true);
		   try {
		    //f.getName()得到对应字段的属性名，f.get(o)得到对应字段属性值,f.getGenericType()得到对应字段的类型
			   if( f.get(o) !=null )
		    System.out.println("属性名："+f.getName()+"；属性值："+f.get(o)+";字段类型：" + f.getGenericType());
		   } catch (IllegalArgumentException | IllegalAccessException e) {
		    e.printStackTrace();
		   }
		  }
		 }

			 
			 
			 
			 
//		  Student s = new Student();
//		  s.setName("张三");
//		  s.setAge(12);
//		  s.setGrade(89);
//		  reflect(s);
//		 }

}
