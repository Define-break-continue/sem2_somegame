module.exports = function ( grunt ) {
	grunt.initConfig( {
		shell: { 
			server: { /* Подзадача */
				command: 'java -cp L1.2-1.0-jar-with-dependencies.jar main.Main 8080'
				/* запуск сервера */
			}
		},
		fest: {
			templates: { /* Цель */
				files: [{
					expand: true,
					cwd: 'templates', /* исходная директория */
					src: '*.xml', /* имена шаблонов */
					dest: 'public_html/js/tmpl' /* результирующая директория */
				}],
				options: {
					template: function ( data ) {
						return grunt.template.process (
							'var <%= name %>Tmpl = <%= contents %> ;',
							{ data: data }
						);
					}
				}
			}
		},
		watch: {
			fest: { /* Цель */
				files: ['templates/*.xml'], /* следим за шаблонами */
				tasks: ['fest'], /* перекомпилировать */
				options: {
					atBegin: true /* запустить задачу при старте */
				}
			},
			server: {
				files: [ 'public_html/js/**/*.js' ], // следим за JS
				options: {
					livereload: true // автоматическая перезагрузка
				}
			}
		},
		concurrent: {
			target: ['watch', 'shell'],
			options: {
				logConcurrentOutput: true
			}
		}
	} );
	grunt.loadNpmTasks( 'grunt-shell' );
	grunt.loadNpmTasks('grunt-concurrent');
	grunt.loadNpmTasks( 'grunt-fest' );
	grunt.loadNpmTasks( 'grunt-contrib-watch' );
	grunt.registerTask( 'default', [ 'shell', 'watch' ] );
};
