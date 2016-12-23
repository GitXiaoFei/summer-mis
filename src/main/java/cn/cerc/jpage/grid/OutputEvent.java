package cn.cerc.jpage.grid;

import cn.cerc.jpage.grid.lines.AbstractGridLine;

public interface OutputEvent {
	void process(AbstractGridLine line);
}
