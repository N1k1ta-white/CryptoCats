import { Routes } from '@angular/router';
import { MainComponent } from './main/main.component';
import { CollectionComponent } from './collection/collection.component';

export const routes: Routes = [
    { path: '', component: MainComponent },
    { path: 'collection', component: CollectionComponent },
    // { path: 'challenges', component: ChallengesComponent },
];
