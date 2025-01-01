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
package com.ubiqube.etsi.mano.repository.phys;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import com.ubiqube.etsi.mano.repository.AbstractBaseResource;
import com.ubiqube.etsi.mano.repository.RepositoryException;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public class PhysResource extends AbstractBaseResource {

	public PhysResource(final long size, final String fileName) {
		super(size, fileName);
	}

	@Override
	public InputStream getInputStream() {
		try {
			return Files.newInputStream(Paths.get(getFileName()));
		} catch (final NoSuchFileException e) {
			throw new ResourceNotFoundException(e.getMessage(), e);
		} catch (final IOException e) {
			throw new RepositoryException(e);
		}
	}

}
