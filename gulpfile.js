var gulp = require('gulp');
var babel = require('gulp-babel');
var concat = require('gulp-concat');
var sass = require('gulp-sass');
var uglify = require('gulp-uglify');
var uglifycss = require('gulp-uglifycss');

var assetsFolder = 'target/classes/public';

gulp.task('default', ['scss', 'vendor-js', 'js']);

gulp.task('scss', function () {
  return gulp.src('src/main/resources/assets/scss/app.scss')
    .pipe(sass().on('error', sass.logError))
    .pipe(concat('app.css'))
    .pipe(uglifycss({
      "maxLineLen": 120,
      "uglyComments": true
    }))
    .pipe(gulp.dest(assetsFolder + '/css'));
});

gulp.task('vendor-js', function () {
  var files = [
    'node_modules/jquery/dist/jquery.min.js',
    'node_modules/tether/dist/js/tether.min.js',
    'node_modules/bootstrap/dist/js/bootstrap.min.js',
    'node_modules/react/dist/react.min.js',
    'node_modules/react-dom/dist/react-dom.min.js',
    'node_modules/dropzone/dist/min/dropzone.min.js'
  ];

  return gulp.src(files)
    .pipe(uglify({"preserveComments": "all"}))
    .pipe(concat('vendor.js'))
    .pipe(gulp.dest(assetsFolder + '/js'));
});

gulp.task('js', function () {
  var files = [
    'src/main/resources/assets/js/*.js',
    'src/main/resources/assets/jsx/*.jsx'
  ];

  return gulp.src(files)
    .pipe(babel({
      presets: ['es2015', 'react']
    }))
    .pipe(concat('app.js'))
    .pipe(uglify())
    .pipe(gulp.dest(assetsFolder + '/js'));
});
