create table mlm_campaign (
	 campaign_id int AUTO_INCREMENT primary key,
	 campaign_name varchar(255) not null, 
	 reward_length int not null,
	 start_date datetime not null,
	 end_date datetime not null,
	 active int not null,
	 budget int not null,
	 spent int not null,
	 split_type varchar(255),
	 UNIQUE INDEX mlm_campaign_name (campaign_name)
);