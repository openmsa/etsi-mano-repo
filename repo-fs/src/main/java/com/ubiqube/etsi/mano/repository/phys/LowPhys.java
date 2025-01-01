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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.repository.Low;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.repository.RepositoryException;

@Service
public class LowPhys implements Low {

	@Override
	public boolean exist(final String path) {
		return new File(path).exists();
	}

	@Override
	public void mkdir(final String path) {
		new File(path).mkdirs();
	}

	@Override
	public void add(final String path, final byte[] content) {
		try {
			Files.write(Paths.get(path), content);
		} catch (final IOException e) {
			throw new RepositoryException(e);
		}

	}

	@Override
	public void add(final String path, final InputStream stream) {
		try {
			Files.copy(stream, Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
		} catch (final IOException e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public ManoResource get(final String path) {
		final Path p = Paths.get(path);
		return new PhysResource(p.toFile().length(), path);
	}

	@Override
	public void delete(final String path) {
		boolean result;
		try {
			result = deleteRecursively(Paths.get(path));
		} catch (final IOException e) {
			throw new RepositoryException(e);
		}
		if (!result) {
			throw new RepositoryException("Unable to delete " + path);
		}
	}

	@Override
	public List<String> find(final String inPath, final String pattern) {
		final Path path = Paths.get(inPath);
		try (final Stream<Path> walk = Files.walk(path)) {
			return walk.filter(x -> x.toString().endsWith(pattern))
					.map(Path::toString)
					.toList();
		} catch (final IOException e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public boolean isDirectory(final String _path) {
		return new File(_path).isDirectory();
	}

	@Override
	public ManoResource get(final String path, final int min, final Long max) {
		final Path p = Paths.get(path);
		return new PhysResource(p.toFile().length(), path);
	}

	private static boolean deleteRecursively(final Path root) throws IOException {
		if (!Files.exists(root)) {
			return false;
		}

		Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
				Files.delete(dir);
				return FileVisitResult.CONTINUE;
			}
		});
		return true;
	}
}
