import { Routes } from '@angular/router';
import { MainComponent } from './main/main.component';
import { CollectionComponent } from './collection/collection.component';
import {ReferralsComponent} from "./referrals/referrals.component";

export const routes: Routes = [
    { path: '', component: MainComponent },
    { path: 'collection', component: CollectionComponent },
    { path: 'referrals', component: ReferralsComponent },
    // { path: 'challenges', component: ChallengesComponent },
];
