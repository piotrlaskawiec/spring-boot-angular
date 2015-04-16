'use strict';

angular.module('springAngular', [])
    .service('notesService', ['$http', function($http) {
        this.getAll = function() {
            return $http.get('api/notes/');
        };

        this.add = function(noteContent) {
            return $http.post('api/notes/', noteContent);
        };

        this.remove = function(noteId) {
            return $http.delete('api/notes/'+noteId+'/');
        };
    }])
    .controller('notesController', ['$scope', 'notesService', function($scope, notesService) {
        // load notes
        notesService.getAll().success(function(notes) {
            $scope.notes = notes;
        });

        $scope.addNote = function() {
            notesService.add($scope.note).success(function(note) {
                $scope.notes.push(note);
            });
        };

        $scope.removeNote = function(note) {
            notesService.remove(note.id).success(function() {
                var index = $scope.notes.indexOf(note);
                $scope.notes.splice(index, 1);
            });
        };
    }])
    .directive('notes', function() {
        return {
            restrict: 'E',
            scope: {
                data: '=',
                remove: '&'
            },
            templateUrl: 'views/partial/notes.html'
        }
    });
