import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ResponsavelComponent } from './responsavel/responsavel.component';


const routes: Routes = [
    { path: '', component: ResponsavelComponent }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ResponsavelRoutingModule { }
