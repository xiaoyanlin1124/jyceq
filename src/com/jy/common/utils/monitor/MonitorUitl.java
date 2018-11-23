package com.jy.common.utils.monitor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

import com.sun.management.OperatingSystemMXBean;

public class MonitorUitl {
	
	private static String osName = System.getProperty("os.name");
	/**
	 * 硬盘储存单位[KB]：即1024
	 */
	public static final int STORAGE_UNI_KB=1024;
	/**
	 * 硬盘储存单位[KB]：名称
	 */
	public static final String STORAGE_UNI_KB_STR="KB";
	/**
	 * 硬盘储存单位[MB]：即1024 * 1024
	 */
	public static final int STORAGE_UNI_MB=1048576;
	/**
	 * 硬盘储存单位[MB]名称
	 */
	public static final String STORAGE_UNI_MB_STR="MB";
	/**
	 * 硬盘储存单位[GB]：即1024 * 1024 * 1024
	 */
	public static final int STORAGE_UNI_GB=1073741824;
	/**
	 * 硬盘储存单位[GB]名称
	 */
	public static final String STORAGE_UNI_GB_STR="GB";
	
	private static final int CPUTIME = 2000;
	
	private static final int PERCENT = 100;
	
	private static final int FAULTLENGTH = 10;

	public static String getOsName(){
		if(StringUtils.isBlank(osName))osName = System.getProperty("os.name");
			return osName;
	}
	
	/**
	 * 获取系统环境信息
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> getSysEnvironment() throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		Properties sysProperty=System.getProperties(); //系统属性
		
		InetAddress addr = InetAddress.getLocalHost();  ;  
		String ip = addr.getHostAddress();  
		map.put("localIP", ip);;//本地ip地址
		map.put("localHostName", addr.getHostName());//本地主机名
		map.put("javaVersion", sysProperty.getProperty("java.version"));//Java的运行环境版本
		map.put("javaVendor", sysProperty.getProperty("java.vendor"));//Java的运行环境供应商
		map.put("javaVendorUrl", sysProperty.getProperty("java.vendor.url"));//Java供应商的URL
		
		map.put("javaHome", sysProperty.getProperty("java.home"));//Java的安装路径
		map.put("javaIoTmpdir", sysProperty.getProperty("java.io.tmpdir"));//默认的临时文件路径
		map.put("osName", sysProperty.getProperty("os.name"));//操作系统的名称
		map.put("osArch", sysProperty.getProperty("os.arch"));//操作系统的构架
		map.put("osVersion", sysProperty.getProperty("os.version"));//操作系统的版本		
		
		
		return map;
	}
	/**
	 * 获取CPU信息
	 * @return
	 */
	public static Map<String,Object> getCPUInfo(){
		Map<String,Object> map=new HashMap<String,Object>();
		double cpuUsage=getCpuUsage();
		map.put("cpuUsage",cpuUsage);//CPU使用率
		map.put("availableProcessors",Runtime.getRuntime().availableProcessors());//CPU个数	
		return map;
	}
	
	/**
	 * 获取硬盘容量
	 * @param storageUni 硬盘储存单位(可参考工具类的常量STORAGE_UNI_XX)
	 * @return
	 */
	public static List<Map<String, Object>> getDiskInfo(int storageUni) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		File[] disks = File.listRoots();
		for (File file : disks) {
			Map<String, Object> disk = new HashMap<String, Object>();
			disk.put("name", file.getPath());// 硬盘名称
			disk.put("free", file.getFreeSpace() / storageUni);// 空闲空间
			//file.get
			disk.put("use", (file.getTotalSpace()-file.getFreeSpace()) / storageUni);// 已经使用空间
			disk.put("total", file.getTotalSpace() / storageUni);// 总容量
			if (storageUni == STORAGE_UNI_KB) {// 单位
				disk.put("uni", STORAGE_UNI_KB_STR);
			} else if (storageUni == STORAGE_UNI_MB) {
				disk.put("uni", STORAGE_UNI_MB_STR);
			} else if (storageUni == STORAGE_UNI_GB) {
				disk.put("uni", STORAGE_UNI_GB_STR);
			}
			list.add(disk);
		}
		return list;
	}
	  
	/**
	 * 获取内存容量
	 * @param storageUni 硬盘储存单位(可参考工具类的常量STORAGE_UNI_XX)
	 * @return
	 */ 
	public static Map<String, Object> getMemInfo(int storageUni) {
		Map<String, Object> map = new HashMap<String, Object>();
		InputStreamReader inputs = null;
		BufferedReader buffer = null;
		try {
			if (getOsName().toLowerCase().contains("windows") || getOsName().toLowerCase().contains("win")) {
				OperatingSystemMXBean mem = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
				long freePhysicalMemorySize = mem.getFreePhysicalMemorySize();
				long totalPhysicalMemorySize = mem.getTotalPhysicalMemorySize();
				map.put("useRam", (totalPhysicalMemorySize - freePhysicalMemorySize) / storageUni);// 使用内存
				map.put("freeRam", freePhysicalMemorySize / storageUni);// 空闲内存
				map.put("totalRam", totalPhysicalMemorySize / storageUni);// 总内存

				long freeSwapSpaceSize = mem.getFreeSwapSpaceSize();
				long totalSwapSpaceSize = mem.getTotalSwapSpaceSize();
				long freeSwap=freeSwapSpaceSize-freePhysicalMemorySize;
				map.put("uesSwap", (totalSwapSpaceSize-totalPhysicalMemorySize-freeSwap) / storageUni);// 使用虚拟内存			
				map.put("freeSwap", freeSwap / storageUni);// 空闲虚拟内存
				map.put("totalSwap", (totalSwapSpaceSize-totalPhysicalMemorySize) / storageUni);// 总虚拟内存

			} else {
										
				Map<String, Object> temporary_map = new HashMap<String, Object>();
				inputs = new InputStreamReader(new FileInputStream("/proc/meminfo"));
				buffer = new BufferedReader(inputs);
				String line = "";
				while (true) {
					line = buffer.readLine();
					if (line == null)
						break;
					int beginIndex = 0;
					int endIndex = line.indexOf(":");
					if (endIndex != -1) {
						String key = line.substring(beginIndex, endIndex);
						beginIndex = endIndex + 1;
						endIndex = line.length();
						String memory = line.substring(beginIndex, endIndex);
						String value = memory.replace("kB", "").trim();
						temporary_map.put(key, value);
					}
				}
				long memTotal = Long.parseLong(temporary_map.get("MemTotal").toString());
				long memFree = Long.parseLong(temporary_map.get("MemFree").toString());
				long memused = memTotal - memFree;
				long buffers = Long.parseLong(temporary_map.get("Buffers").toString());
				long cached = Long.parseLong(temporary_map.get("Cached").toString());
				long usage = memused - buffers - cached;
				map.put("useRam", usage / storageUni);// 使用内存
				map.put("freeRam", memFree / storageUni);// 空闲内存
				map.put("totalRam", memTotal / storageUni);// 总内存
											
				//map.put("uesSwap", usage / storageUni);// 空闲虚拟内存
				//map.put("freeSwap", freeSwapSpaceSize / storageUni);// 空闲虚拟内存
				//map.put("totalSwap", cached/ storageUni);// 总虚拟内存
				
			}
						
			Runtime runtime = Runtime.getRuntime();
			map.put("uesMemory", runtime.totalMemory() / storageUni);// 程序内存总量
			map.put("freeMemory", runtime.freeMemory() / storageUni);// 程序空闲内存量
			map.put("totalMemory", runtime.maxMemory() / storageUni);// 程序使用最大内存量
			if (storageUni == STORAGE_UNI_KB) {// 单位
				map.put("uni", STORAGE_UNI_KB_STR);
			} else if (storageUni == STORAGE_UNI_MB) {
				map.put("uni", STORAGE_UNI_MB_STR);
			} else if (storageUni == STORAGE_UNI_GB) {
				map.put("uni", STORAGE_UNI_GB_STR);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(inputs,buffer);		
		}
		return map;
	}
	
	private static void close(InputStreamReader inputs,BufferedReader buffer){
		try {
			if(buffer!=null)
				buffer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(inputs!=null)
				inputs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * 功能：获取Linux和Window系统CPU使用率
	 */
	public static double getCpuUsage() {
		// 如果是window系统
		if (getOsName().toLowerCase().contains("windows") || getOsName().toLowerCase().contains("win")) {
			try {
				String procCmd = System.getenv("windir")
						+ "//system32//wbem//wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
				Thread.sleep(CPUTIME);// 睡眠再娶数
				// 取进程信息
				long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));// 第一次读取CPU信息
				Thread.sleep(CPUTIME);// 睡眠再娶数
				long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));// 第二次读取CPU信息
				if (c0 != null && c1 != null) {
					long idletime = c1[0] - c0[0];// 空闲时间
					long busytime = c1[1] - c0[1];// 使用时间
					Double cpusage = Double.valueOf(PERCENT * (busytime) * 1.0 / (busytime + idletime));
					BigDecimal b1 = new BigDecimal(cpusage);
					double cpuUsage = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					return cpuUsage;
				} else {
					return 0.0;
				}
			} catch (Exception ex) {
				return 0.0;
			}
		} else {
			try {
				Map<?, ?> map1 =cpuinfo();
				Thread.sleep(CPUTIME);
				Map<?, ?> map2 =cpuinfo();
				long user1 = Long.parseLong(map1.get("user").toString());
				long nice1 = Long.parseLong(map1.get("nice").toString());
				long system1 = Long.parseLong(map1.get("system").toString());
				long idle1 = Long.parseLong(map1.get("idle").toString());

				long user2 = Long.parseLong(map2.get("user").toString());
				long nice2 = Long.parseLong(map2.get("nice").toString());
				long system2 = Long.parseLong(map2.get("system").toString());
				long idle2 = Long.parseLong(map2.get("idle").toString());

				long total1 = user1 + system1 + nice1;
				long total2 = user2 + system2 + nice2;
				float total = total2 - total1;

				long totalIdle1 = user1 + nice1 + system1 + idle1;
				long totalIdle2 = user2 + nice2 + system2 + idle2;
				float totalidle = totalIdle2 - totalIdle1;

				float cpusage = (total / totalidle) * 100;

				BigDecimal b1 = new BigDecimal(cpusage);
				double cpuUsage = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				return cpuUsage;
			} catch (Exception e) {
			}
		}
		return 0;
	}
	
	// window读取cpu相关信息
	private static long[] readCpu(final Process proc) {
		long[] retn = new long[2];
		try {
			proc.getOutputStream().close();
			InputStreamReader ir = new InputStreamReader(proc.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			String line = input.readLine();
			if (line == null || line.length() < FAULTLENGTH) {
				return null;
			}
			int capidx = line.indexOf("Caption");
			int cmdidx = line.indexOf("CommandLine");
			int rocidx = line.indexOf("ReadOperationCount");
			int umtidx = line.indexOf("UserModeTime");
			int kmtidx = line.indexOf("KernelModeTime");
			int wocidx = line.indexOf("WriteOperationCount");
			long idletime = 0;
			long kneltime = 0;// 读取物理设备时间
			long usertime = 0;// 执行代码占用时间
			while ((line = input.readLine()) != null) {
				if (line.length() < wocidx) {
					continue;
				}
				// 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount
				String caption = substring(line, capidx, cmdidx - 1).trim();
				String cmd = substring(line, cmdidx, kmtidx - 1).trim();

				if (cmd.indexOf("wmic.exe") >= 0) {
					continue;
				}
				String s1 = substring(line, kmtidx, rocidx - 1).trim();
				String s2 = substring(line, umtidx, wocidx - 1).trim();
				List<String> digitS1 = getDigit(s1);
				List<String> digitS2 = getDigit(s2);

				if (caption.equals("System Idle Process") || caption.equals("System")) {
					if (s1.length() > 0) {
						if (!digitS1.get(0).equals("") && digitS1.get(0) != null) {
							idletime += Long.valueOf(digitS1.get(0)).longValue();
						}
					}
					if (s2.length() > 0) {
						if (!digitS2.get(0).equals("") && digitS2.get(0) != null) {
							idletime += Long.valueOf(digitS2.get(0)).longValue();
						}
					}
					continue;
				}
				if (s1.length() > 0) {
					if (!digitS1.get(0).equals("") && digitS1.get(0) != null) {
						kneltime += Long.valueOf(digitS1.get(0)).longValue();
					}
				}

				if (s2.length() > 0) {
					if (!digitS2.get(0).equals("") && digitS2.get(0) != null) {
						kneltime += Long.valueOf(digitS2.get(0)).longValue();
					}
				}
			}
			retn[0] = idletime;
			retn[1] = kneltime + usertime;

			return retn;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				proc.getInputStream().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 功能：Linux CPU使用信息
	 */
	public static Map<?, ?> cpuinfo() {
		InputStreamReader inputs = null;
		BufferedReader buffer = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			inputs = new InputStreamReader(new FileInputStream("/proc/stat"));
			buffer = new BufferedReader(inputs);
			String line = "";
			while (true) {
				line = buffer.readLine();
				if (line == null) {
					break;
				}
				if (line.startsWith("cpu")) {
					StringTokenizer tokenizer = new StringTokenizer(line);
					List<String> temp = new ArrayList<String>();
					while (tokenizer.hasMoreElements()) {
						String value = tokenizer.nextToken();
						temp.add(value);
					}
					map.put("user", temp.get(1));
					map.put("nice", temp.get(2));
					map.put("system", temp.get(3));
					map.put("idle", temp.get(4));
					map.put("iowait", temp.get(5));
					map.put("irq", temp.get(6));
					map.put("softirq", temp.get(7));
					map.put("stealstolen", temp.get(8));
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				close(inputs,buffer);
			} catch (Exception e2) {
			
			}
		}
		return map;
	}
	
	/**
	 * 从字符串文本中获得数字
	 * @param text
	 * @return
	 */
	private static List<String> getDigit(String text) {
		List<String> digitList = new ArrayList<String>();
		digitList.add(text.replaceAll("\\D", ""));
		return digitList;
	}

	/**
	 * 由于String.subString对汉字处理存在问题（把一个汉字视为一个字节)，因此在 包含汉字的字符串时存在隐患，现调整如下：
	 * @param src 要截取的字符串
	 * @param start_idx 开始坐标（包括该坐标)
	 * @param end_idx 截止坐标（包括该坐标）
	 * @return
	 */
	private static String substring(String src, int start_idx, int end_idx) {
		byte[] b = src.getBytes();
		String tgt = "";
		for (int i = start_idx; i <= end_idx; i++) {
			tgt += (char) b[i];
		}
		return tgt;
	}
}
