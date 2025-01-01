/**
 * Copyright (C) 2019-2025 Ubiqube.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.repository.gridfs;

import java.io.InputStream;

import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.repository.ResetOnCloseInputStream;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public class ManoGridFsResource implements ManoResource {
	private long size;
	private InputStream inputStream;
	private String fileName;

	public ManoGridFsResource(final long size, final InputStream inputStream, final String fileName) {
		super();
		this.size = size;
		this.inputStream = new ResetOnCloseInputStream(inputStream);
		this.fileName = fileName;
	}

	@Override
	public long getSize() {
		return size;
	}

	public void setSize(final long size) {
		this.size = size;
	}

	@Override
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(final InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}

}
