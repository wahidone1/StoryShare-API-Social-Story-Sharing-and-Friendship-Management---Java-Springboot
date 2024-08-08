# Story API

Story API adalah sebuah aplikasi backend yang dikembangkan dengan Spring Boot untuk mengelola cerita (story), pertemanan, dan autentikasi pengguna. API ini menyediakan berbagai endpoint untuk operasi CRUD (Create, Read, Update, Delete) pada cerita, serta fitur manajemen pertemanan.

## Fitur

- **Autentikasi**: Login dan logout pengguna.
- **Pengelolaan Pengguna**: Registrasi, mendapatkan detail pengguna saat ini, dan memperbarui informasi pengguna.
- **Cerita (Story)**: Membuat, menghapus, dan mendapatkan cerita dari teman-teman.
- **Pertemanan**: Menambahkan teman, menghapus teman, dan melihat daftar teman.

## Prasyarat

- Java 17 atau yang lebih baru
- Maven 3.6.0 atau yang lebih baru
- MySQL atau database lain yang kompatibel
- Postman atau alat API client lainnya (untuk pengujian API)

## Instalasi

1. Clone repositori ini ke dalam komputer Anda:

   ```bash
   git clone https://github.com/username/story-api.git
   ```

2. Masuk ke dalam direktori proyek:

   ```bash
   cd story-api
   ```

3. Buat file `application.properties` di dalam direktori `src/main/resources` dan tambahkan konfigurasi database:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/story_api
   spring.datasource.username=root
   spring.datasource.password=password
   spring.jpa.hibernate.ddl-auto=update
   springdoc.api-docs.version=OPENAPI_3_0
   ```

4. Bangun proyek menggunakan Maven:

   ```bash
   mvn clean install
   ```

5. Jalankan aplikasi:

   ```bash
   mvn spring-boot:run
   ```

## Dokumentasi API

Dokumentasi API tersedia di Swagger UI setelah aplikasi berjalan. Anda dapat mengaksesnya melalui URL berikut:

```
http://localhost:8080/swagger-ui/index.html
```

### Endpoints

#### Autentikasi

- **Login**: `POST /api/auth/login`
- **Logout**: `DELETE /api/auth/logout`

#### Pengelolaan Pengguna

- **Registrasi**: `POST /api/users`
- **Dapatkan Pengguna Saat Ini**: `GET /api/users/current`
- **Perbarui Pengguna Saat Ini**: `PATCH /api/users/current`

#### Cerita (Story)

- **Buat Cerita Baru**: `POST /api/stories`
- **Hapus Cerita**: `DELETE /api/stories/{storyId}`
- **Dapatkan Cerita Teman**: `GET /api/stories/home`

#### Pertemanan

- **Tambah Teman**: `POST /api/friendships`
- **Daftar Teman**: `GET /api/friendships/list`
- **Hapus Teman**: `DELETE /api/friendships/{friendUsername}`

## Struktur Proyek

- **Controllers**: Mengelola endpoint API dan logika pemrosesan request.
- **Services**: Mengandung logika bisnis aplikasi.
- **Models**: Menyimpan model data yang digunakan di seluruh aplikasi.
- **Entities**: Representasi tabel di database.

## Kontribusi

Kontribusi selalu diterima! Jika ingin berkontribusi, silakan lakukan pull request dengan perubahan yang Anda buat.
