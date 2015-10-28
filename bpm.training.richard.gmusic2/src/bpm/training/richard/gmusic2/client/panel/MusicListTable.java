package bpm.training.richard.gmusic2.client.panel;

import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialToast;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import bpm.training.richard.gmusic2.client.page.HomePage;
import bpm.training.richard.gmusic2.client.services.IMusicService;
import bpm.training.richard.gmusic2.shared.Music;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;

public class MusicListTable extends Composite {

	private DataGrid<Music> dataGrid;
	private ListDataProvider<Music> musicProvider = new ListDataProvider<Music>();
	private ListHandler<Music> sortHandler;
	
	private List<Music> allMusic = new ArrayList<Music>();
	private Music music = new Music();
	private HomePage homepage;
	 
	@UiField SimplePanel gridPanel, pagerPanel;
	@UiField MaterialLabel lblTotal;
	
	private static MusicListTableUiBinder uiBinder = GWT
			.create(MusicListTableUiBinder.class);

	interface MusicListTableUiBinder extends UiBinder<Widget, MusicListTable> {
	}

	public MusicListTable() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public MusicListTable(HomePage homepage) {
		initWidget(uiBinder.createAndBindUi(this));
		setGrid();
		this.homepage = homepage;
	}
	
	private void setGrid() {
		dataGrid = createGrid();
		dataGrid.setEmptyTableWidget(new HTML("No data to Display"));
		gridPanel.setWidget(dataGrid);
		refreshData();
	}
	
	private void refreshData() {
		getMusic();
	}
	
	private void getMusic() {
		MaterialLoader.showLoading(true);
		IMusicService.Connect.getService().getAllMusicByUser(new AsyncCallback<List<Music>>() {

			@Override
			public void onFailure(Throwable caught) {
				MaterialLoader.showLoading(false);
				MaterialToast.alert(caught.getMessage());
			}

			@Override
			public void onSuccess(List<Music> result) {
				MusicListTable.this.allMusic = result;
				populateTable(result);
				MaterialLoader.showLoading(false);
				MaterialToast.alert("Success");
			}
			
		});
	}
	
	private void populateTable(List<Music> result) {
		allMusic = result;
		lblTotal.setText("Total Songs : " + allMusic.size());
		//musicProvider.setList(allMusic);
		musicProvider.setList(allMusic);
		sortHandler.setList(musicProvider.getList());
		
	}
	
	private DataGrid<Music> createGrid() {
		this.sortHandler = new ListHandler<Music>(new ArrayList<Music>());
		TextColumn<Music> colTitle = new TextColumn<Music>() {
			@Override
			public String getValue(Music obj) {

				return String.valueOf(obj.getTitle());
			}
		};
		sortHandler.setComparator(colTitle, new Comparator<Music>(){
			public int compare(Music o1, Music o2) {
				return o1.getTitle().compareToIgnoreCase(o2.getTitle());
			}
		});
		colTitle.setSortable(true);
		
		TextColumn<Music> colArtist = new TextColumn<Music>() {
			@Override
			public String getValue(Music obj) {

				return String.valueOf(obj.getArtist());
			}
		};
		sortHandler.setComparator(colArtist, new Comparator<Music>(){
			public int compare(Music o1, Music o2) {
				return o1.getArtist().compareToIgnoreCase(o2.getArtist());
			}
		});
		colArtist.setSortable(true);
		
		TextColumn<Music> colDesc = new TextColumn<Music>() {
			@Override
			public String getValue(Music obj) {

				return String.valueOf(obj.getDescription());
			}
		};
		sortHandler.setComparator(colDesc, new Comparator<Music>(){
			public int compare(Music o1, Music o2) {
				return o1.getDescription().compareToIgnoreCase(o2.getDescription());
			}
		});
		colDesc.setSortable(true);
		
		final ProvidesKey<Music> KEY_PROVIDER = new ProvidesKey<Music>() {

			@Override
			public Object getKey(Music item) {
				return item.getId();
			}
		};
		
		final DataGrid<Music> dataGrid = new DataGrid<Music>(100, KEY_PROVIDER); 
				
		dataGrid.setSize("100%", "75vh");
		dataGrid.addColumn(colTitle, "Music Title");
		dataGrid.addColumn(colArtist, "Music Artist");
		dataGrid.addColumn(colDesc, "Description");
		
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setDisplay(dataGrid);
		
		musicProvider.addDataDisplay(dataGrid);
		dataGrid.addColumnSortHandler(sortHandler);
		
		return dataGrid;
	}

}
