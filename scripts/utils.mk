define list_to_string
	$(subst $(1),$(2),$(foreach item,$(3),$(item)))
endef