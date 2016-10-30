package cn.cerc.jmis.message;

public enum MessageLevel {
	General, // 通用消息
	Great, // 重要消息，需要在首页予滚动展示
	Grave, // 紧急消息，需要弹窗提示
	Logger, // 日志类消息，默认为已读
	Service, // 后台任务
	Printer // 打印任务
}
