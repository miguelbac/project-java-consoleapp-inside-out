package org.factoriaf5.project_inside_out.repositories;

import org.factoriaf5.project_inside_out.models.Emotion;
import org.factoriaf5.project_inside_out.models.Movie;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CsvMovieRepository implements MovieRepository {
    private final File file;

    public CsvMovieRepository() {
        File folder = new File("exports");
        if (!folder.exists())
            folder.mkdirs();
        this.file = new File(folder, "movies.csv");
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("ImdbId,Título,Géneros,Emoción,Año,FechaCreación\n");
            } catch (IOException e) {
                throw new RuntimeException("Error creando archivo CSV de películas", e);
            }
        }
    }

    @Override
    public Movie save(Movie movie) {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(String.format("%s,%s,%s,%d,%d,%s\n",
                    movie.getImdbId(),
                    escapeCsv(movie.getTitle()),
                    escapeCsv(String.join(";", movie.getGenres())),
                    movie.getEmotion().getOption(),
                    movie.getReleaseYear(),
                    movie.getCreatedAt()));
        } catch (IOException e) {
            throw new RuntimeException("Error guardando película en CSV", e);
        }
        return movie;
    }

    @Override
    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            br.readLine(); // saltar cabecera
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 6) {
                    String title = parts[1].replaceAll("^\"|\"$", "");
                    String genresStr = parts[2].replaceAll("^\"|\"$", "");
                    List<String> genres = List.of(genresStr.split(";"));
                    
                    Movie movie = new Movie(
                            parts[0],
                            title,
                            genres,
                            Emotion.fromOption(Integer.parseInt(parts[3])),
                            Integer.parseInt(parts[4]),
                            java.time.LocalDateTime.parse(parts[5]));
                    movies.add(movie);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo películas del CSV", e);
        }
        return movies;
    }

    @Override
    public void deleteById(String imdbId) {
        List<Movie> movies = findAll().stream()
                .filter(m -> !m.getImdbId().equals(imdbId))
                .collect(Collectors.toList());
        
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("ImdbId,Título,Géneros,Emoción,Año,FechaCreación\n");
            for (Movie m : movies) {
                writer.write(String.format("%s,%s,%s,%d,%d,%s\n",
                        m.getImdbId(),
                        escapeCsv(m.getTitle()),
                        escapeCsv(String.join(";", m.getGenres())),
                        m.getEmotion().getOption(),
                        m.getReleaseYear(),
                        m.getCreatedAt()));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar película", e);
        }
    }

    @Override
    public List<Movie> findByGenre(String genre) {
        return findAll().stream()
                .filter(m -> m.getGenres().stream()
                        .map(String::toLowerCase)
                        .anyMatch(g -> g.contains(genre.toLowerCase())))
                .collect(Collectors.toList());
    }

    private String escapeCsv(String text) {
        if (text == null)
            return "";
        String escaped = text.replace("\"", "\"\"");
        return "\"" + escaped + "\"";
    }
}