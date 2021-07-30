import { Component, OnInit, ViewChild } from '@angular/core';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { Table } from 'primeng';
import { finalize } from 'rxjs/operators';
import { ResponsavelDocument } from 'src/app/models/responsavel';
import { Filter } from 'src/app/shared/filters/filter';
import { Page } from 'src/app/shared/page';
import { ResponsavelService } from 'src/app/shared/services/responsavel.service';

@Component({
    selector: 'app-responsavel',
    templateUrl: './responsavel.component.html',
    styleUrls: ['./responsavel.component.css']
})
export class ResponsavelComponent implements OnInit {

    @BlockUI() blockUI: NgBlockUI;

    responsaveis: Page<ResponsavelDocument> = new Page;
    filtro = new Filter();

    rows = 20;

    @ViewChild('tabela') datatable: Table;

    constructor(private responsavelService: ResponsavelService) { }

    ngOnInit(): void {
        this.search();
    }

    search() {
        this.blockUI.start('Carregando');
        this.responsavelService.search(this.filtro, this.datatable).pipe(finalize(() => this.blockUI.stop()))
            .subscribe(res => {
                this.responsaveis = res
                console.log(this.responsaveis);
            });
    }

    exibirResponsavel(responsavel: ResponsavelDocument) {
        console.log(responsavel);
    }

}
